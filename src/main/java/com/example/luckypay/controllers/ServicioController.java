package com.example.luckypay.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.luckypay.dtos.ServicioDto;
import com.example.luckypay.dtos.ServicioDtoResponse;
import com.example.luckypay.services.ServicioServices;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/servicios")
@RequiredArgsConstructor
public class ServicioController {

    private final ServicioServices servSer;

    // Crear
    @PostMapping("/crear")
    public ResponseEntity<ServicioDtoResponse> saveServ(@Valid @RequestBody ServicioDto dto) {

        ServicioDtoResponse serv = servSer.guardar(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(serv);
    }

    // Consultar los servicios activos
    @GetMapping("/activos")
    public ResponseEntity<List<ServicioDtoResponse>> activosServ() {
        return ResponseEntity.ok(servSer.listarActivos());
    }

    // Consulta Servicio por id
    @GetMapping("/{id}")
    public ResponseEntity<ServicioDtoResponse> servId(@PathVariable Long id) {
        return ResponseEntity.ok(servSer.servId(id));
    }

    // Consulta Servicio por id oculto
    @GetMapping("/serv")
    public ResponseEntity<ServicioDtoResponse> servId(@RequestBody ServicioDto dto) {
        Long id = dto.getId();
        return ResponseEntity.ok(servSer.servId(id));
    }

    // Actualiza Servicio por el Id
    @PutMapping("/{id}")
    public ResponseEntity<ServicioDtoResponse> actualizaServ(@PathVariable Long id,
            @Valid @RequestBody ServicioDto dto) {

        ServicioDtoResponse servActualizado = servSer.servUpdate(id, dto);

        return ResponseEntity.ok(servActualizado);
    }

    // Actualiza Servicio por el Id
    // Ocultando Id
    @PutMapping("/serv")
    public ResponseEntity<ServicioDtoResponse> actualizaServ(@Valid @RequestBody ServicioDto dto) {

        Long id = dto.getId();

        ServicioDtoResponse servActualizado = servSer.servUpdate(id, dto);

        return ResponseEntity.ok(servActualizado);
    }

    // Activar Servicio
    @PutMapping("/{id}/activar")
    public ResponseEntity<ServicioDtoResponse> activarServ(@PathVariable Long id) {
        return ResponseEntity.ok(servSer.activar(id));
    }

    // Desactivar Servicio
    @PutMapping("/{id}/desactivar")
    public ResponseEntity<ServicioDtoResponse> desactivarServ(@PathVariable Long id) {
        return ResponseEntity.ok(servSer.desactivar(id));
    }
} 