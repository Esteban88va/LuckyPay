package com.example.luckypay.services;

import java.util.List;

import com.example.luckypay.dtos.UsuarioDto;
import com.example.luckypay.dtos.UsuarioDtoResponse;

public interface UsuarioService {

     // registrar un nuevo usuario
    UsuarioDtoResponse guardar(UsuarioDto usuarioDto);

    // Listar todos los usuarios
    List<UsuarioDtoResponse> listarTodos();

    // consultar usuario por id
    UsuarioDtoResponse buscarPorId(Long id);

    // consultar usuario por numero de documento
    UsuarioDtoResponse buscarPorNumDoc(Long numDoc);

    // Actualizar Usuario
    UsuarioDtoResponse actualizar(Long id, UsuarioDto usuarioDto);

    // Eliminar Usuario con cuerpo
    void eliminar(Long id);

}

