package com.example.luckypay.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetalleOrdenDto {

    private Long idDetalleOrden;

    // Nombre del servicio agregado a la orden
    private String servicio;

    // Precio que tenía el servicio al agregarlo
    private BigDecimal precio;

}