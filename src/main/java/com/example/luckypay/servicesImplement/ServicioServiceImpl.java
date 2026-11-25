package com.example.luckypay.servicesImplement;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.luckypay.dtos.ServicioDto;
import com.example.luckypay.dtos.ServicioDtoResponse;
import com.example.luckypay.mappers.ServicioMapper;
import com.example.luckypay.models.Servicio;
import com.example.luckypay.repositoryes.ServicioRepository;
import com.example.luckypay.services.ServicioServices;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicioServiceImpl implements ServicioServices {

    private final ServicioRepository servRepo;
    private final ServicioMapper servMapper;

    // Guardar o crear Servicio
    @Override
    @Transactional
    public ServicioDtoResponse guardar(ServicioDto dto) {

        // 1. Validar que no exista un servicio con el mismo nombre
        if (servRepo.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new IllegalArgumentException(
                    "Ya existe un servicio con el nombre: " + dto.getNombre());
        }

        // 2. Convertir DTO en entidad
        Servicio servicio = servMapper.toEntidad(dto);
        // 3. El id es autoincremental y cada servicio nuevo nace activo
        servicio.setId(null);
        servicio.setActivo(true);

        // 4. Guardar servicio
        Servicio servicioGuardado = servRepo.save(servicio);

        // 5. Retornar los datos del servicio
        return servMapper.toDto(servicioGuardado);
    }

    // Consultar solo los servicios activos
    @Override
    @Transactional(readOnly = true)
    public List<ServicioDtoResponse> listarActivos() {
        return servRepo.findByActivoTrue()
                .stream()
                .map(servMapper::toDto)
                .toList();
    }

    // Servicio por Id
    @Override
    @Transactional(readOnly = true)
    public ServicioDtoResponse servId(Long id) {
        Servicio servicio = servRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontro servicio con Id: " + id));

        return servMapper.toDto(servicio);
    }

    // Actualizar datos del Servicio (editar)
    @Override
    @Transactional
    public ServicioDtoResponse servUpdate(Long id, ServicioDto dto) {
        Servicio servExis = servRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontro servicio con Id: " + id));

        // Si cambió el nombre, validar que el nuevo no esté en uso
        if (!servExis.getNombre().equalsIgnoreCase(dto.getNombre())
                && servRepo.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new IllegalArgumentException(
                    "Ya existe un servicio con el nombre: " + dto.getNombre());
        }

        servExis.setNombre(dto.getNombre());
        servExis.setDescripcion(dto.getDescripcion());
        servExis.setPrecio(dto.getPrecio());
        servExis.setDuracionEstimada(dto.getDuracionEstimada());

        Servicio servActualizado = servRepo.save(servExis);

        return servMapper.toDto(servActualizado);
    }

    // Activar Servicio
    @Override
    @Transactional
    public ServicioDtoResponse activar(Long id) {
        return cambiarEstado(id, true);
    }

    // Desactivar Servicio
    @Override
    @Transactional
    public ServicioDtoResponse desactivar(Long id) {
        return cambiarEstado(id, false);
    }

    // Cambia el estado (activo) de un servicio
    private ServicioDtoResponse cambiarEstado(Long id, boolean estado) {
        Servicio servicio = servRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontro servicio con Id: " + id));

        servicio.setActivo(estado);

        return servMapper.toDto(servRepo.save(servicio));
    }
}