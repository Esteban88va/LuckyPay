package com.example.luckypay.services;

import com.example.luckypay.dtos.AdministradorDto;

public interface AdministradorServices {

    // Registrar el administrador (solo puede existir uno)
    AdministradorDto guardar(AdministradorDto dto);

    // Consultar administrador por id
    AdministradorDto buscarPorId(Long id);

    // Actualizar administrador
    AdministradorDto actualizar(Long id, AdministradorDto dto);

    // Activar y desactivar (atributo activo)
    AdministradorDto activar(Long id);

    AdministradorDto desactivar(Long id);
}