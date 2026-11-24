package com.example.luckypay.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.luckypay.dtos.UsuarioDto;
import com.example.luckypay.services.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor 
@RestController 
@RequestMapping ("/api/usuarios")
public class UsuarioController {
        private final UsuarioService usuarioService;


    // 1. Registrar un nuevo usuario
    @PostMapping
    public ResponseEntity <UsuarioDto> guardar(@Valid @RequestBody UsuarioDto usuarioDto) {
        
        UsuarioDto usuarioguardado = usuarioService.guardar(usuarioDto);
        return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(usuarioguardado)
        ;
    }

}

