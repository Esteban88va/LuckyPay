package com.example.luckypay.services;

import java.util.List;

import com.example.luckypay.dtos.ServicioDto;
import com.example.luckypay.dtos.ServicioDtoResponse;

public interface ServicioServices {

    ServicioDtoResponse guardar(ServicioDto dto);

    List<ServicioDtoResponse> listarActivos();

    ServicioDtoResponse servId(Long id);

    ServicioDtoResponse servUpdate(Long id, ServicioDto dto);

    ServicioDtoResponse activar(Long id);

    ServicioDtoResponse desactivar(Long id);

}