package com.example.luckypay.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// DTO de respuesta: datos que se devuelven al consultar un proveedor
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProveedorDtoResponse {

    private Long id;
    private String nombre;
    private String nit;
    private String telefono;
    private String correo;
    private boolean activo;
}
