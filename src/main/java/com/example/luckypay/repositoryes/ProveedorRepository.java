package com.example.luckypay.repositoryes;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.luckypay.models.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    // metodo para verificar si ya existe un proveedor con ese NIT
    boolean existsByNit(String nit);
}
