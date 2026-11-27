package com.example.luckypay.servicesImplement;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.luckypay.dtos.ProveedorDto;
import com.example.luckypay.dtos.ProveedorDtoResponse;
import com.example.luckypay.mappers.ProveedorMapper;
import com.example.luckypay.models.Proveedor;
import com.example.luckypay.repositoryes.ProveedorRepository;
import com.example.luckypay.services.ProveedorServices;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProveedorServiceImpl implements ProveedorServices {

    private final ProveedorRepository provRepo;
    private final ProveedorMapper provMapper;

    // Guardar o crear Proveedor
    @Override
    @Transactional
    public ProveedorDtoResponse guardar(ProveedorDto dto) {

        // 1. Validar que no exista un proveedor con el mismo NIT
        if (provRepo.existsByNit(dto.getNit())) {
            throw new IllegalArgumentException(
                    "Ya existe un proveedor con el NIT: " + dto.getNit());
        }

        // 2. Convertir DTO en entidad
        Proveedor proveedor = provMapper.toEntidad(dto);

       // 3. El id es autoincremental y cada proveedor nuevo nace activo
        proveedor.setId(null);
        proveedor.setActivo(true);

        // 4. Guardar proveedor
        Proveedor proveedorGuardado = provRepo.save(proveedor);

        // 5. Retornar los datos del proveedor
        return provMapper.toDto(proveedorGuardado);
    }

    // Proveedor por Id
    @Override
    @Transactional(readOnly = true)
    public ProveedorDtoResponse provId(Long id) {
        return provMapper.toDto(buscarEntidad(id));
    }

    // Actualizar datos del Proveedor (editar)
    @Override
    @Transactional
    public ProveedorDtoResponse provUpdate(Long id, ProveedorDto dto) {
        Proveedor provExis = buscarEntidad(id);

        // Si cambió el NIT, validar que el nuevo no esté en uso
        if (!provExis.getNit().equals(dto.getNit())
                && provRepo.existsByNit(dto.getNit())) {
            throw new IllegalArgumentException(
                    "Ya existe un proveedor con el NIT: " + dto.getNit());
        }

        provExis.setNombre(dto.getNombre());
        provExis.setNit(dto.getNit());
        provExis.setTelefono(dto.getTelefono());
        provExis.setCorreo(dto.getCorreo());

        Proveedor provActualizado = provRepo.save(provExis);

        return provMapper.toDto(provActualizado);
    }

    // Activar Proveedor
    @Override
    @Transactional
    public ProveedorDtoResponse activar(Long id) {
        return cambiarEstado(id, true);
    }

    // Desactivar Proveedor
    @Override
    @Transactional
    public ProveedorDtoResponse desactivar(Long id) {
        return cambiarEstado(id, false);
    }

    // Cambia el estado (activo) de un proveedor
    private ProveedorDtoResponse cambiarEstado(Long id, boolean estado) {
        Proveedor proveedor = buscarEntidad(id);
        proveedor.setActivo(estado);
        return provMapper.toDto(provRepo.save(proveedor));
    }

    private Proveedor buscarEntidad(Long id) {
        return provRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontro proveedor con Id: " + id));
    }
}