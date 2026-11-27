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

import com.example.luckypay.dtos.ProveedorDto;
import com.example.luckypay.dtos.ProveedorDtoResponse;
import com.example.luckypay.services.ProveedorServices;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/proveedores")
@RequiredArgsConstructor
public class ProveedorController {

    private final ProveedorServices provSer;

    // Crear
    @PostMapping("/crear")
    public ResponseEntity<ProveedorDtoResponse> saveProv(@Valid @RequestBody ProveedorDto dto) {

        ProveedorDtoResponse prov = provSer.guardar(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(prov);
    }

    // Consulta Proveedor por id
    @GetMapping("/{id}")
    public ResponseEntity<ProveedorDtoResponse> provId(@PathVariable Long id) {
        return ResponseEntity.ok(provSer.provId(id));
    }

    // Consulta Proveedor por id ocultado
    @GetMapping("/prov")
    public ResponseEntity<ProveedorDtoResponse> provId(@RequestBody ProveedorDto dto) {
        return ResponseEntity.ok(provSer.provId(dto.getId()));
    }

    // Actualiza Proveedor por el Id
    @PutMapping("/{id}")
    public ResponseEntity<ProveedorDtoResponse> actualizaProv(@PathVariable Long id,
            @Valid @RequestBody ProveedorDto dto) {
        return ResponseEntity.ok(provSer.provUpdate(id, dto));
    }

    // Actualiza Proveedor ocultando el Id
    @PutMapping("/prov")
    public ResponseEntity<ProveedorDtoResponse> actualizaProv(@Valid @RequestBody ProveedorDto dto) {
        return ResponseEntity.ok(provSer.provUpdate(dto.getId(), dto));
    }

    // Activar Proveedor
    @PutMapping("/{id}/activar")
    public ResponseEntity<ProveedorDtoResponse> activarProv(@PathVariable Long id) {
        return ResponseEntity.ok(provSer.activar(id));
    }

    // Desactivar Proveedor
    @PutMapping("/{id}/desactivar")
    public ResponseEntity<ProveedorDtoResponse> desactivarProv(@PathVariable Long id) {
        return ResponseEntity.ok(provSer.desactivar(id));
    }
}
