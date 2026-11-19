package com.example.luckypay.dtos;

import com.example.luckypay.enums.Rol;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@AllArgsConstructor 
@NoArgsConstructor 
@SuperBuilder 
public class UsuarioDto {
        private Long id;
@JsonProperty("nomb")
@NotBlank(message = "El nombre es obligatorio")
@Size(min = 2, max = 60, message = "El nombre debe tener entre 2 y 60 caracteres")
private String nom;

@JsonProperty("apell")
private String apell;

@JsonProperty("corr")
@NotBlank(message = "El correo es obligatorio")
@Email(message = "El correo debe tener un formato válido")
private String corr;

@JsonProperty("direc")
private String direc;

@JsonProperty("tele")
@NotBlank(message = "El teléfono es obligatorio")
@Pattern(regexp = "^[0-9]{10}$", message = "El teléfono debe tener 10 dígitos")
private String tele;

@NotNull(message = "El rol es obligatorio")
private Rol rol;
}



