package com.example.luckypay.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.luckypay.dtos.ServicioDto;
import com.example.luckypay.dtos.ServicioDtoResponse;
import com.example.luckypay.models.Servicio;

@Mapper(componentModel = "spring")
public interface ServicioMapper {

    // 1. De DTO a Entidad
    // el id lo genera la base de datos y el estado (activo) lo controla el service
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activo", ignore = true)
    Servicio toEntidad(ServicioDto servicioDto);

    // 2. De Entidad a DTO de respuesta
    ServicioDtoResponse toDto(Servicio servicio);
}