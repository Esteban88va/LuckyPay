package com.example.luckypay.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
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


  // tipoDoc no puede ser nulo
    @NotNull(message = "El tipo de documento es obligatorio")
    private Byte tipoDoc;

    // numDoc no puede ser nulo y debe ser positivo
    @NotNull(message = "El número de documento es obligatorio")
    @Positive(message = "El número de documento debe ser positivo")
    private Long numDoc;

    // Credenciales para el registro
    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(max = 80, message = "El usuario no puede superar los 80 caracteres")
    private String user;

    @NotBlank(message = "La contraseña es obligatoria")
    // la propiedad password no se incluirá en la serialización JSON de salida, pero
    // sí se permitirá en la deserialización JSON de entrada
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 8, max = 20, message = "La contraseña debe tener entre 8 y 20 caracteres")
    private String password;


}



