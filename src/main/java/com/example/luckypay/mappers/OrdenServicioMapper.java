package com.example.luckypay.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.luckypay.dtos.DetalleOrdenDto;
import com.example.luckypay.dtos.OrdenServicioDto;
import com.example.luckypay.models.DetalleOrden;
import com.example.luckypay.models.OrdenServicio;

@Mapper(componentModel = "spring")
public interface OrdenServicioMapper {

    // De Entidad a DTO (el estado sale como texto y los detalles se convierten
    // con el método de abajo)
    @Mapping(source = "estado.nombre", target = "estado")
    OrdenServicioDto toDto(OrdenServicio orden);

    // De DTO a Entidad (el resto lo controla el service)
    @Mapping(target = "idOrden", ignore = true)
    @Mapping(target = "fechaIngreso", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "costoTotal", ignore = true)
    @Mapping(target = "detalles", ignore = true)
    OrdenServicio toEntidad(OrdenServicioDto dto);

    // Detalle de la orden: el servicio sale como su nombre
    @Mapping(source = "servicio.nombre", target = "servicio")
    DetalleOrdenDto toDetalleDto(DetalleOrden detalle);
}