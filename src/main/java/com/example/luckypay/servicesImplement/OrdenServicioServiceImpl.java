package com.example.luckypay.servicesImplement;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.luckypay.dtos.OrdenServicioDto;
import com.example.luckypay.mappers.OrdenServicioMapper;
import com.example.luckypay.models.DetalleOrden;
import com.example.luckypay.models.EstadoOrden;
import com.example.luckypay.models.OrdenServicio;
import com.example.luckypay.models.Servicio;
import com.example.luckypay.repositoryes.DetalleOrdenRepository;
import com.example.luckypay.repositoryes.EstadoOrdenRepository;
import com.example.luckypay.repositoryes.OrdenServicioRepository;
import com.example.luckypay.repositoryes.ServicioRepository;
import com.example.luckypay.services.OrdenServicioServices;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrdenServicioServiceImpl implements OrdenServicioServices {

    private static final List<String> ESTADOS_VALIDOS = List.of(
            "AGENDADA", "RECIBIDA", "EN_PROCESO", "FINALIZADA", "CANCELADA");

    // Estados en los que todavía se pueden agregar o quitar servicios
    private static final List<String> ESTADOS_ABIERTOS = List.of(
            "AGENDADA", "RECIBIDA", "EN_PROCESO");

    private final OrdenServicioRepository ordenRepository;
    private final EstadoOrdenRepository estadoRepository;
    private final DetalleOrdenRepository detalleRepository;
    private final ServicioRepository servicioRepository;
    private final OrdenServicioMapper ordenMapper;

    // Agendar
    @Override
    @Transactional
    public OrdenServicioDto agendar(OrdenServicioDto dto) {
        OrdenServicio orden = ordenMapper.toEntidad(dto);
        orden.setEstado(obtenerEstado("AGENDADA"));
        orden.setCostoTotal(BigDecimal.ZERO);

        return ordenMapper.toDto(ordenRepository.save(orden));
    }

    // Consultar por id
    @Override
    @Transactional(readOnly = true)
    public OrdenServicioDto consultarPorId(Long id) {
        return ordenMapper.toDto(buscarEntidad(id));
    }

    // Cancelar
    @Override
    @Transactional
    public OrdenServicioDto cancelar(Long id) {
        OrdenServicio orden = buscarEntidad(id);
        String actual = orden.getEstado().getNombre();

        if (actual.equals("FINALIZADA") || actual.equals("CANCELADA")) {
            throw new IllegalStateException(
                    "No se puede cancelar una orden en estado " + actual);
        }

        orden.setEstado(obtenerEstado("CANCELADA"));
        return ordenMapper.toDto(ordenRepository.save(orden));
    }

    // Reprogramar
    @Override
    @Transactional
    public OrdenServicioDto reprogramar(Long id, LocalDateTime nuevaFecha) {
        OrdenServicio orden = buscarEntidad(id);

        if (!orden.getEstado().getNombre().equals("AGENDADA")) {
            throw new IllegalStateException(
                    "Solo se puede reprogramar una orden agendada");
        }
        if (nuevaFecha.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La nueva fecha debe ser futura");
        }

        orden.setFechaAgendada(nuevaFecha);
        return ordenMapper.toDto(ordenRepository.save(orden));
    }

    // Recibir la bicicleta
    @Override
    @Transactional
    public OrdenServicioDto recibir(Long id) {
        OrdenServicio orden = buscarEntidad(id);

        if (!orden.getEstado().getNombre().equals("AGENDADA")) {
            throw new IllegalStateException(
                    "Solo se puede recibir una orden agendada");
        }

        orden.setFechaIngreso(LocalDateTime.now());
        orden.setEstado(obtenerEstado("RECIBIDA"));
        return ordenMapper.toDto(ordenRepository.save(orden));
    }

    // Cambiar estado
    @Override
    @Transactional
    public OrdenServicioDto cambiarEstado(Long id, String nuevoEstado) {
        String nombre = nuevoEstado.trim().toUpperCase();

        if (!ESTADOS_VALIDOS.contains(nombre)) {
            throw new IllegalArgumentException(
                    "Estado no válido. Use uno de: " + ESTADOS_VALIDOS);
        }

        OrdenServicio orden = buscarEntidad(id);
        orden.setEstado(obtenerEstado(nombre));
        return ordenMapper.toDto(ordenRepository.save(orden));
    }

    // Agregar un servicio a la orden
    @Override
    @Transactional
    public OrdenServicioDto agregarServicio(Long idOrden, Long idServicio) {
        OrdenServicio orden = buscarEntidad(idOrden);
        validarOrdenAbierta(orden);

        Servicio servicio = servicioRepository.findById(idServicio)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontró el servicio con id: " + idServicio));

        if (!servicio.isActivo()) {
            throw new IllegalStateException("El servicio no está activo");
        }

        // El precio se copia en este momento: si el servicio cambia de precio
        // después, esta orden conserva el que tenía al agregarlo
        DetalleOrden detalle = detalleRepository.save(DetalleOrden.builder()
                .orden(orden)
                .servicio(servicio)
                .precio(servicio.getPrecio())
                .build());

        orden.getDetalles().add(detalle);
        recalcularCosto(orden);

        return ordenMapper.toDto(ordenRepository.save(orden));
    }

    // Quitar un servicio de la orden
    @Override
    @Transactional
    public OrdenServicioDto quitarServicio(Long idOrden, Long idDetalle) {
        OrdenServicio orden = buscarEntidad(idOrden);
        validarOrdenAbierta(orden);

        boolean quitado = orden.getDetalles()
                .removeIf(d -> d.getIdDetalleOrden().equals(idDetalle));

        if (!quitado) {
            throw new EntityNotFoundException(
                    "La orden " + idOrden + " no tiene el detalle con id: " + idDetalle);
        }

        recalcularCosto(orden);

        return ordenMapper.toDto(ordenRepository.save(orden));
    }

    // Calcular costo
    @Override
    @Transactional
    public OrdenServicioDto calcularCosto(Long idOrden) {
        OrdenServicio orden = buscarEntidad(idOrden);
        recalcularCosto(orden);

        return ordenMapper.toDto(ordenRepository.save(orden));
    }

    // Agenda de un día
    @Override
    @Transactional(readOnly = true)
    public List<OrdenServicioDto> consultarAgenda(LocalDate fecha) {
        LocalDateTime inicio = fecha.atStartOfDay();
        LocalDateTime fin = fecha.atTime(LocalTime.MAX);

        return ordenRepository
                .findByFechaAgendadaBetweenOrderByFechaAgendadaAsc(inicio, fin)
                .stream()
                .map(ordenMapper::toDto)
                .toList();
    }

    // Historial
    @Override
    @Transactional(readOnly = true)
    public List<OrdenServicioDto> consultarHistorial(String estado,
            LocalDateTime desde, LocalDateTime hasta) {
        String filtro = (estado == null || estado.isBlank())
                ? null
                : estado.trim().toUpperCase();

        return ordenRepository.consultarHistorial(filtro, desde, hasta)
                .stream()
                .map(ordenMapper::toDto)
                .toList();
    }

    private OrdenServicio buscarEntidad(Long id) {
        return ordenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontró la orden con id: " + id));
    }

    // Solo se tocan los servicios de órdenes que siguen abiertas
    private void validarOrdenAbierta(OrdenServicio orden) {
        String estado = orden.getEstado().getNombre();

        if (!ESTADOS_ABIERTOS.contains(estado)) {
            throw new IllegalStateException(
                    "No se pueden modificar los servicios de una orden en estado " + estado);
        }
    }

    // Suma el precio de cada detalle y lo deja en costoTotal
    private void recalcularCosto(OrdenServicio orden) {
        BigDecimal total = orden.getDetalles().stream()
                .map(DetalleOrden::getPrecio)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        orden.setCostoTotal(total);
    }

    // Busca el estado y, si aún no existe en la base de datos, lo crea
    private EstadoOrden obtenerEstado(String nombre) {
        return estadoRepository.findByNombre(nombre)
                .orElseGet(() -> estadoRepository.save(
                        EstadoOrden.builder().nombre(nombre).build()));
    }
}