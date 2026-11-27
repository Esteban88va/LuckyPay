package com.example.luckypay.services;

import java.util.List;

import com.example.luckypay.dtos.TipoServicioDto;
import com.example.luckypay.dtos.TipoServicioDtoResponse;

public interface TipoServicioServices {

    TipoServicioDtoResponse guardar(TipoServicioDto dto);

    List<TipoServicioDtoResponse> listarActivos();

    TipoServicioDtoResponse tipoId(Long id);

    TipoServicioDtoResponse tipoUpdate(Long id, TipoServicioDto dto);

    TipoServicioDtoResponse activar(Long id);

    TipoServicioDtoResponse desactivar(Long id);

}
