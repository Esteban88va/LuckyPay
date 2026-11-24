package com.example.luckypay.models;

import com.example.luckypay.models.usuarios.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "administradores")
@PrimaryKeyJoinColumn(name = "administrador_id")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Administrador extends Usuario {

    @Builder.Default
    @Column(name = "activo", nullable = false)
    private boolean activo = true;

}