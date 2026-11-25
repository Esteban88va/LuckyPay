package com.example.luckypay.repositoryes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.luckypay.models.Servicio;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {

    // metodo para listar solo los servicios activos
    List<Servicio> findByActivoTrue();

    // metodo para verificar si ya existe un servicio con ese nombre
    boolean existsByNombreIgnoreCase(String nombre);
}