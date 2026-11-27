package com.example.luckypay.repositoryes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.luckypay.models.TipoServicio;

public interface TipoServicioRepository extends JpaRepository<TipoServicio, Long> {

    // metodo para listar solo los tipos de servicio activos
    List<TipoServicio> findByActivoTrue();

    // metodo para verificar si ya existe un tipo de servicio con ese nombre
    boolean existsByNombreIgnoreCase(String nombre);
}
