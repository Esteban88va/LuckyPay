package com.example.luckypay.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// DTO de respuesta: datos que se devuelven al consultar un tipo de servicio
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoServicioDtoResponse {

    private Long id;
    private String nombre;
    private String descripcion;
    private boolean activo;
}
