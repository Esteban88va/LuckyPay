package com.example.luckypay.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.luckypay.dtos.AdministradorDto;
import com.example.luckypay.services.AdministradorServices;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/administradores")
@RequiredArgsConstructor
public class AdministradorController {

    private final AdministradorServices administradorServices;

    // Registrar el administrador
    @PostMapping
    public ResponseEntity<AdministradorDto> guardar(@Valid @RequestBody AdministradorDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(administradorServices.guardar(dto));
    }

    // Consultar por id
    @GetMapping("/{id}")
    public ResponseEntity<AdministradorDto> consultaId(@PathVariable Long id) {
        return ResponseEntity.ok(administradorServices.buscarPorId(id));
    }

    // Consultar por POST (id oculto)
    @PostMapping("/buscar")
    public ResponseEntity<AdministradorDto> consultaIdPost(@RequestBody AdministradorDto dto) {
        return ResponseEntity.ok(administradorServices.buscarPorId(dto.getId()));
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<AdministradorDto> update(@PathVariable Long id,
            @RequestBody AdministradorDto dto) {
        return ResponseEntity.ok(administradorServices.actualizar(id, dto));
    }

    // Actualizar ocultando el id
    @PutMapping("/actualizar")
    public ResponseEntity<AdministradorDto> update2(@RequestBody AdministradorDto dto) {
        return ResponseEntity.ok(administradorServices.actualizar(dto.getId(), dto));
    }

    // Activar
    @PutMapping("/{id}/activar")
    public ResponseEntity<AdministradorDto> activar(@PathVariable Long id) {
        return ResponseEntity.ok(administradorServices.activar(id));
    }

    // Desactivar
    @PutMapping("/{id}/desactivar")
    public ResponseEntity<AdministradorDto> desactivar(@PathVariable Long id) {
        return ResponseEntity.ok(administradorServices.desactivar(id));
    }
}