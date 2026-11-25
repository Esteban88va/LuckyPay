package com.example.luckypay.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// DTO de entrada: datos que llegan al crear o editar un servicio
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicioDto {

    private Long id;

    // nombre no puede ser nulo ni vacío, máximo 100 caracteres
    @NotBlank(message = "El nombre del servicio es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String nombre;

    @Size(max = 255, message = "La descripción no puede superar los 255 caracteres")
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.00", message = "El precio no puede ser negativo")
    private BigDecimal precio;

    // Duración estimada en minutos
    @NotNull(message = "La duración estimada es obligatoria")
    @Positive(message = "La duración debe ser mayor a 0 minutos")
    private Integer duracionEstimada;
}