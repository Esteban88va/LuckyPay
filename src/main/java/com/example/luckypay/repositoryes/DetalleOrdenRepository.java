package com.example.luckypay.repositoryes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.luckypay.models.DetalleOrden;

public interface DetalleOrdenRepository extends JpaRepository<DetalleOrden, Long> {

    // Todos los detalles de una orden
    List<DetalleOrden> findByOrdenIdOrden(Long idOrden);
}