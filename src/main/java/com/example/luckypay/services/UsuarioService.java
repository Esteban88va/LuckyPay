package com.example.luckypay.services;

import com.example.luckypay.dtos.UsuarioDto;

public interface UsuarioService {

    // registrar un nuevo usuario
     UsuarioDto guardar(UsuarioDto usuarioDto);
}
