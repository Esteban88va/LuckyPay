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

import com.example.luckypay.dtos.TipoServicioDto;
import com.example.luckypay.dtos.TipoServicioDtoResponse;
import com.example.luckypay.services.TipoServicioServices;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/tipos-servicio")
@RequiredArgsConstructor
public class TipoServicioController {

    private final TipoServicioServices tipoSer;

    // Crear
    @PostMapping("/crear")
    public ResponseEntity<TipoServicioDtoResponse> saveTipo(@Valid @RequestBody TipoServicioDto dto) {

        TipoServicioDtoResponse tipo = tipoSer.guardar(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(tipo);
    }

    // Consultar los tipos de servicio activos
    @GetMapping("/activos")
    public ResponseEntity<List<TipoServicioDtoResponse>> activosTipo() {
        return ResponseEntity.ok(tipoSer.listarActivos());
    }

    // Consulta Tipo de Servicio por id
    @GetMapping("/{id}")
    public ResponseEntity<TipoServicioDtoResponse> tipoId(@PathVariable Long id) {
        return ResponseEntity.ok(tipoSer.tipoId(id));
    }

    // Consulta Tipo de Servicio por id ocultado
    @GetMapping("/tipo")
    public ResponseEntity<TipoServicioDtoResponse> tipoId(@RequestBody TipoServicioDto dto) {
        return ResponseEntity.ok(tipoSer.tipoId(dto.getId()));
    }

    // Actualiza Tipo de Servicio por el Id
    @PutMapping("/{id}")
    public ResponseEntity<TipoServicioDtoResponse> actualizaTipo(@PathVariable Long id,
            @Valid @RequestBody TipoServicioDto dto) {
        return ResponseEntity.ok(tipoSer.tipoUpdate(id, dto));
    }

    // Actualiza Tipo de Servicio ocultando el Id
    @PutMapping("/tipo")
    public ResponseEntity<TipoServicioDtoResponse> actualizaTipo(@Valid @RequestBody TipoServicioDto dto) {
        return ResponseEntity.ok(tipoSer.tipoUpdate(dto.getId(), dto));
    }

    // Activar Tipo de Servicio
    @PutMapping("/{id}/activar")
    public ResponseEntity<TipoServicioDtoResponse> activarTipo(@PathVariable Long id) {
        return ResponseEntity.ok(tipoSer.activar(id));
    }

    // Desactivar Tipo de Servicio
    @PutMapping("/{id}/desactivar")
    public ResponseEntity<TipoServicioDtoResponse> desactivarTipo(@PathVariable Long id) {
        return ResponseEntity.ok(tipoSer.desactivar(id));
    }
}
