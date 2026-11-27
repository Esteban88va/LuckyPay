package com.example.luckypay.services;

import com.example.luckypay.dtos.ProveedorDto;
import com.example.luckypay.dtos.ProveedorDtoResponse;

public interface ProveedorServices {

    ProveedorDtoResponse guardar(ProveedorDto dto);

    ProveedorDtoResponse provId(Long id);

    ProveedorDtoResponse provUpdate(Long id, ProveedorDto dto);

    ProveedorDtoResponse activar(Long id);

    ProveedorDtoResponse desactivar(Long id);

}
