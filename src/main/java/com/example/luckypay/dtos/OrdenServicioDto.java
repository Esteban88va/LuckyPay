package com.example.luckypay.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class OrdenServicioDto {

    private Long idOrden;

    @NotNull(message = "La fecha agendada es obligatoria")
    @Future(message = "La fecha agendada debe ser futura")
    private LocalDateTime fechaAgendada;

    // La llena el sistema cuando se recibe la bicicleta
    private LocalDateTime fechaIngreso;

    @NotBlank(message = "La descripción del problema es obligatoria")
    @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
    private String descripcionProblema;

    // Solo de salida: nombre del estado (AGENDADA, RECIBIDA...)
    private String estado;

    // Solo de salida: lo calcula el sistema
    private BigDecimal costoTotal;

    // Solo de salida: servicios agregados a la orden
    private List<DetalleOrdenDto> detalles;

}