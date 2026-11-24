package com.example.luckypay.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.luckypay.dtos.UsuarioDto;
import com.example.luckypay.dtos.UsuarioDtoResponse;
import com.example.luckypay.models.usuarios.Usuario;


@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    // convertir de Entidad a DTO
    @Mapping(source = "nom", target = "nomb")
    @Mapping(source = "ape", target = "apell")
    @Mapping(source = "dir", target = "direc")
    @Mapping(source = "tel", target = "tele")
    @Mapping(source = "tipoDoc.nombre", target = "tipoDoc")
    @Mapping(source = "numDoc", target = "numDoc")
    UsuarioDtoResponse convierteaDto(Usuario usuario);

    // convertir de DTO a Entidad
    @Mapping(source = "nomb", target = "nom")
    @Mapping(source = "apell", target = "ape")
    @Mapping(source = "direc", target = "dir")
    @Mapping(source = "tele", target = "tel")
    @Mapping(source = "numDoc", target = "numDoc")

    // Las relaciones se asignan desde el services
    @Mapping(target = "tipoDoc", ignore = true)
    Usuario convierteaEntidad(UsuarioDto usuarioDto);

}