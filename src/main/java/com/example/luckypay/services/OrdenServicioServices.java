package com.example.luckypay.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.example.luckypay.dtos.OrdenServicioDto;

public interface OrdenServicioServices {

    // Agendar una orden nueva
    OrdenServicioDto agendar(OrdenServicioDto dto);

    // Consultar una orden por id
    OrdenServicioDto consultarPorId(Long id);

    // Cancelar
    OrdenServicioDto cancelar(Long id);

    // Reprogramar
    OrdenServicioDto reprogramar(Long id, LocalDateTime nuevaFecha);

    // Recibir la bicicleta en el taller
    OrdenServicioDto recibir(Long id);

    // Cambiar el estado
    OrdenServicioDto cambiarEstado(Long id, String nuevoEstado);

    // Agregar un servicio a la orden
    OrdenServicioDto agregarServicio(Long idOrden, Long idServicio);

    // Quitar un servicio de la orden (por el id del detalle)
    OrdenServicioDto quitarServicio(Long idOrden, Long idDetalle);

    // Recalcular el costo total sumando los servicios de la orden
    OrdenServicioDto calcularCosto(Long idOrden);

    // Órdenes agendadas en un día
    List<OrdenServicioDto> consultarAgenda(LocalDate fecha);

    // Historial con filtros opcionales
    List<OrdenServicioDto> consultarHistorial(String estado, LocalDateTime desde,
            LocalDateTime hasta);
}