package com.example.luckypay.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.luckypay.dtos.OrdenServicioDto;
import com.example.luckypay.services.OrdenServicioServices;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/ordenes-servicio")
@RequiredArgsConstructor
public class OrdenServicioController {

    private final OrdenServicioServices ordenServices;

    // Agendar una orden
    @PostMapping
    public ResponseEntity<OrdenServicioDto> agendar(@Valid @RequestBody OrdenServicioDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ordenServices.agendar(dto));
    }

    // Agenda de un día: GET /api/ordenes-servicio/agenda?fecha=2026-12-01
    @GetMapping("/agenda")
    public ResponseEntity<List<OrdenServicioDto>> consultarAgenda(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(ordenServices.consultarAgenda(fecha));
    }

    // Historial con filtros opcionales (estado, desde, hasta)
    @GetMapping("/historial")
    public ResponseEntity<List<OrdenServicioDto>> consultarHistorial(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {
        return ResponseEntity.ok(ordenServices.consultarHistorial(estado, desde, hasta));
    }

    // Consultar por id
    @GetMapping("/{id}")
    public ResponseEntity<OrdenServicioDto> consultaId(@PathVariable Long id) {
        return ResponseEntity.ok(ordenServices.consultarPorId(id));
    }

    // Cancelar
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<OrdenServicioDto> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(ordenServices.cancelar(id));
    }

    // Reprogramar: PUT /api/ordenes-servicio/1/reprogramar?nuevaFecha=2026-12-05T10:00:00
    @PutMapping("/{id}/reprogramar")
    public ResponseEntity<OrdenServicioDto> reprogramar(@PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime nuevaFecha) {
        return ResponseEntity.ok(ordenServices.reprogramar(id, nuevaFecha));
    }

    // Recibir la bicicleta
    @PutMapping("/{id}/recibir")
    public ResponseEntity<OrdenServicioDto> recibir(@PathVariable Long id) {
        return ResponseEntity.ok(ordenServices.recibir(id));
    }

    // Cambiar estado: PUT /api/ordenes-servicio/1/estado?nuevo=EN_PROCESO
    @PutMapping("/{id}/estado")
    public ResponseEntity<OrdenServicioDto> cambiarEstado(@PathVariable Long id,
            @RequestParam String nuevo) {
        return ResponseEntity.ok(ordenServices.cambiarEstado(id, nuevo));
    }

    // Agregar servicio: POST /api/ordenes-servicio/1/servicios/2
    @PostMapping("/{id}/servicios/{idServicio}")
    public ResponseEntity<OrdenServicioDto> agregarServicio(@PathVariable Long id,
            @PathVariable Long idServicio) {
        return ResponseEntity.ok(ordenServices.agregarServicio(id, idServicio));
    }

    // Quitar servicio: DELETE /api/ordenes-servicio/1/servicios/5 (5 = id del detalle)
    @DeleteMapping("/{id}/servicios/{idDetalle}")
    public ResponseEntity<OrdenServicioDto> quitarServicio(@PathVariable Long id,
            @PathVariable Long idDetalle) {
        return ResponseEntity.ok(ordenServices.quitarServicio(id, idDetalle));
    }

    // Recalcular el costo total
    @PutMapping("/{id}/calcular-costo")
    public ResponseEntity<OrdenServicioDto> calcularCosto(@PathVariable Long id) {
        return ResponseEntity.ok(ordenServices.calcularCosto(id));
    }
}