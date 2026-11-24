package com.example.luckypay.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.luckypay.dtos.UsuarioDto;
import com.example.luckypay.models.usuarios.Usuario;


@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    // Entidad -> DTO
    @Mapping(source = "ape", target = "apell")
    @Mapping(source = "dir", target = "direc")
    @Mapping(source = "tel", target = "tele")
    UsuarioDto convierteaDto(Usuario usuario);

    // DTO -> Entidad
    @Mapping(source = "apell", target = "ape")
    @Mapping(source = "direc", target = "dir")
    @Mapping(source = "tele", target = "tel")
    Usuario convierteaEntidad(UsuarioDto dto);
}