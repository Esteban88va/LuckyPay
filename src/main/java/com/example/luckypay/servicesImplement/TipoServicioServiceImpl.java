package com.example.luckypay.servicesImplement;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.luckypay.dtos.TipoServicioDto;
import com.example.luckypay.dtos.TipoServicioDtoResponse;
import com.example.luckypay.mappers.TipoServicioMapper;
import com.example.luckypay.models.TipoServicio;
import com.example.luckypay.repositoryes.TipoServicioRepository;
import com.example.luckypay.services.TipoServicioServices;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TipoServicioServiceImpl implements TipoServicioServices {

    private final TipoServicioRepository tipoRepo;
    private final TipoServicioMapper tipoMapper;

    // Guardar o crear Tipo de Servicio
    @Override
    @Transactional
    public TipoServicioDtoResponse guardar(TipoServicioDto dto) {

        // 1. Validar que no exista un tipo de servicio con el mismo nombre
        if (tipoRepo.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new IllegalArgumentException(
                    "Ya existe un tipo de servicio con el nombre: " + dto.getNombre());
        }

        // 2. Convertir DTO en entidad
        TipoServicio tipo = tipoMapper.toEntidad(dto);

        // 3. El id es autoincremental y cada tipo nuevo nace activo
        tipo.setId(null);
        tipo.setActivo(true);

        // 4. Guardar tipo de servicio
        TipoServicio tipoGuardado = tipoRepo.save(tipo);

        // 5. Retornar los datos del tipo de servicio
        return tipoMapper.toDto(tipoGuardado);
    }

    // Consultar solo los tipos de servicio activos
    @Override
    @Transactional(readOnly = true)
    public List<TipoServicioDtoResponse> listarActivos() {
        return tipoRepo.findByActivoTrue()
                .stream()
                .map(tipoMapper::toDto)
                .toList();
    }

    // Tipo de servicio por Id
    @Override
    @Transactional(readOnly = true)
    public TipoServicioDtoResponse tipoId(Long id) {
        return tipoMapper.toDto(buscarEntidad(id));
    }

    // Actualizar datos del Tipo de Servicio (editar)
    @Override
    @Transactional
    public TipoServicioDtoResponse tipoUpdate(Long id, TipoServicioDto dto) {
        TipoServicio tipoExis = buscarEntidad(id);

        // Si cambió el nombre, validar que el nuevo no esté en uso
        if (!tipoExis.getNombre().equalsIgnoreCase(dto.getNombre())
                && tipoRepo.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new IllegalArgumentException(
                    "Ya existe un tipo de servicio con el nombre: " + dto.getNombre());
        }

        tipoExis.setNombre(dto.getNombre());
        tipoExis.setDescripcion(dto.getDescripcion());

        TipoServicio tipoActualizado = tipoRepo.save(tipoExis);

        return tipoMapper.toDto(tipoActualizado);
    }

    // Activar Tipo de Servicio
    @Override
    @Transactional
    public TipoServicioDtoResponse activar(Long id) {
        return cambiarEstado(id, true);
    }

    // Desactivar Tipo de Servicio
    @Override
    @Transactional
    public TipoServicioDtoResponse desactivar(Long id) {
        return cambiarEstado(id, false);
    }

    // Cambia el estado (activo) de un tipo de servicio
    private TipoServicioDtoResponse cambiarEstado(Long id, boolean estado) {
        TipoServicio tipo = buscarEntidad(id);
        tipo.setActivo(estado);
        return tipoMapper.toDto(tipoRepo.save(tipo));
    }

    private TipoServicio buscarEntidad(Long id) {
        return tipoRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontro tipo de servicio con Id: " + id));
    }
}
