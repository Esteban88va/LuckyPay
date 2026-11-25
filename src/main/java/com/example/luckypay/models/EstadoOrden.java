package com.example.luckypay.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "estados_orden")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstadoOrden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Ej: AGENDADA, RECIBIDA, EN_PROCESO, FINALIZADA, CANCELADA
    @Column(name = "nombre", nullable = false, unique = true, length = 30)
    private String nombre;

}