package com.example.luckypay.repositoryes;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.luckypay.models.auth.UsuarioAuth;

public interface UsuarioAuthRepository extends JpaRepository<UsuarioAuth, Long> {

    boolean existsByUser(String user);

    Optional<UsuarioAuth> findByUser(String user);

}