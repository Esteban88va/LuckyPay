package com.example.luckypay.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.luckypay.dtos.TipoServicioDto;
import com.example.luckypay.dtos.TipoServicioDtoResponse;
import com.example.luckypay.models.TipoServicio;

@Mapper(componentModel = "spring")
public interface TipoServicioMapper {

    // 1. De DTO a Entidad
    // el id lo genera la base de datos y el estado (activo) lo controla el service
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activo", ignore = true)
    TipoServicio toEntidad(TipoServicioDto tipoServicioDto);

    // 2. De Entidad a DTO de respuesta
    TipoServicioDtoResponse toDto(TipoServicio tipoServicio);
}
