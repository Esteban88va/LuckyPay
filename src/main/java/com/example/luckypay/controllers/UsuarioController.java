package com.example.luckypay.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.luckypay.dtos.UsuarioDto;
import com.example.luckypay.dtos.UsuarioDtoResponse;
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
    public ResponseEntity <UsuarioDtoResponse> guardar(@Valid @RequestBody UsuarioDto usuarioDto) {
        
        UsuarioDtoResponse usuarioguardado = usuarioService.guardar(usuarioDto);
        return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(usuarioguardado)
        ;
    }
     //metodo para probar el fucniconamiento de la aplicacion
     @GetMapping("")
    public String inicio() {
    return "Saludo desde el controlador del Usuario controller";
    }

    
    // Consulta usuario por Id
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDtoResponse> consutaId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    // consulta por post
    @PostMapping("/buscar")
    public ResponseEntity<UsuarioDtoResponse> consutaIdpost(@RequestBody UsuarioDto dto) {
        UsuarioDtoResponse encontrado = usuarioService.buscarPorId(dto.getId());

        return ResponseEntity.ok(encontrado);
    }

    // Consulta usuario por numero de documento
    @GetMapping("/buscaid")
    public ResponseEntity<UsuarioDtoResponse> consutaNumDoc(@RequestBody UsuarioDto dto) {
        UsuarioDtoResponse encontrado = usuarioService.buscarPorNumDoc(dto.getNumDoc());

        return ResponseEntity.ok(encontrado);
    }

    // actualizar
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDtoResponse> update(
            @PathVariable Long id,
            @RequestBody UsuarioDto dto) {

        UsuarioDtoResponse usuarioActualizado = usuarioService.actualizar(
                id, dto);

        return ResponseEntity.ok(usuarioActualizado);
    }

    // actualizar ocultando ID
    @PutMapping("/actualizar")
    public ResponseEntity<UsuarioDtoResponse> update2(
            @RequestBody UsuarioDto dto) {
        Long id = dto.getId();
        UsuarioDtoResponse usuarioActualizado = usuarioService.actualizar(
                id, dto);

        return ResponseEntity.ok(usuarioActualizado);
    }

    // Eliminar Usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {

        usuarioService.eliminar(id);

        return ResponseEntity.ok("Usuario con id :" + id + " Eliminado correctamente");
    }

    //Consultar todos los Usuarios de la Base de datos
 
@GetMapping("/todos")
public List<UsuarioDtoResponse> allUsers() {
    return usuarioService.listarTodos();
}
}

