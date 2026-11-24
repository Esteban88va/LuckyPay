package com.example.luckypay.repositoryes;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.luckypay.models.auth.Rol;

public interface RolRepository extends JpaRepository<Rol, Byte> {

    // Busca un rol por su nombre (ej. "ROLE_ADMIN").
    @Query("select r from Rol r where r.nombre = :nombre")
    Optional<Rol> buscarPorNombre(@Param("nombre") String nombre);
}