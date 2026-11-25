package com.example.luckypay.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// DTO de respuesta: datos que se devuelven al consultar un servicio
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicioDtoResponse {

    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer duracionEstimada;
    private boolean activo;
}