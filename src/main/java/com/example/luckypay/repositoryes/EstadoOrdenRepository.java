package com.example.luckypay.repositoryes;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.luckypay.models.EstadoOrden;

public interface EstadoOrdenRepository extends JpaRepository<EstadoOrden, Long> {

    Optional<EstadoOrden> findByNombre(String nombre);
}