package com.example.luckypay.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.luckypay.dtos.AdministradorDto;
import com.example.luckypay.models.Administrador;

@Mapper(componentModel = "spring")
public interface AdministradorMapper {

      // De Entidad a DTO
    @Mapping(source = "ape", target = "apell")
    @Mapping(source = "dir", target = "direc")
    @Mapping(source = "tel", target = "tele")
    @Mapping(target = "tipoDoc", ignore = true)
    @Mapping(target = "numDoc", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "password", ignore = true)
    AdministradorDto toDto(Administrador administrador);

    // De DTO a Entidad (el id y activo los controla el service)
    @Mapping(source = "apell", target = "ape")
    @Mapping(source = "direc", target = "dir")
    @Mapping(source = "tele", target = "tel")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activo", ignore = true)
    Administrador toEntidad(AdministradorDto dto);

    // Copia los datos del DTO sobre un administrador que ya existe (actualizar)
    @Mapping(source = "apell", target = "ape")
    @Mapping(source = "direc", target = "dir")
    @Mapping(source = "tele", target = "tel")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activo", ignore = true)
    void actualizarEntidad(AdministradorDto dto, @MappingTarget Administrador administrador);
}