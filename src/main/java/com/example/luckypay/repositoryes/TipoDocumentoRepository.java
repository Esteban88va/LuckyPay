package com.example.luckypay.repositoryes;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.luckypay.models.usuarios.TipoDocumento;

public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Byte> {

}