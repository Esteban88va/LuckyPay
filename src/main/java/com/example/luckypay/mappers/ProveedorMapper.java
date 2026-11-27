package com.example.luckypay.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.luckypay.dtos.ProveedorDto;
import com.example.luckypay.dtos.ProveedorDtoResponse;
import com.example.luckypay.models.Proveedor;

@Mapper(componentModel = "spring")
public interface ProveedorMapper {

    // 1. De DTO a Entidad
    // el id lo genera la base de datos y el estado (activo) lo controla el service
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activo", ignore = true)
    Proveedor toEntidad(ProveedorDto proveedorDto);

    // 2. De Entidad a DTO de respuesta
    ProveedorDtoResponse toDto(Proveedor proveedor);
}
