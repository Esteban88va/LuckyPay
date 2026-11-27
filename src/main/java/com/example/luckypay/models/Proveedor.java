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
@Table(name = "proveedores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proveedor")
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    // El NIT identifica al proveedor, por eso no se puede repetir
    @Column(name = "nit", nullable = false, unique = true, length = 20)
    private String nit;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "correo", length = 120)
    private String correo;

    // Un proveedor nuevo nace activo (@Builder.Default conserva el true)
    @Builder.Default
    @Column(name = "activo", nullable = false)
    private boolean activo = true;
}
