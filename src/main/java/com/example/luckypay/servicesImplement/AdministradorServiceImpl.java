package com.example.luckypay.servicesImplement;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.luckypay.dtos.AdministradorDto;
import com.example.luckypay.mappers.AdministradorMapper;
import com.example.luckypay.models.Administrador;
import com.example.luckypay.models.auth.Rol;
import com.example.luckypay.models.auth.UsuarioAuth;
import com.example.luckypay.models.auth.UsuarioAuthRol;
import com.example.luckypay.repositoryes.AdministradorRepository;
import com.example.luckypay.repositoryes.RolRepository;
import com.example.luckypay.repositoryes.UsuarioAuthRepository;
import com.example.luckypay.repositoryes.UsuarioAuthRolRepository;
import com.example.luckypay.services.AdministradorServices;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdministradorServiceImpl implements AdministradorServices {

    private final AdministradorRepository administradorRepository;
    private final AdministradorMapper administradorMapper;
    private final UsuarioAuthRepository usuarioAuthRepository;
    private final UsuarioAuthRolRepository usuarioAuthRolRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    // Guardar o crear Administrador
    @Override
    @Transactional
    public AdministradorDto guardar(AdministradorDto dto) {

        // 1. Solo puede existir un administrador en el sistema
        if (administradorRepository.count() > 0) {
            throw new IllegalStateException(
                    "Ya existe un administrador registrado");
        }

                // 2. Validar que el nombre de usuario no exista
        if (usuarioAuthRepository.existsByUser(dto.getUser())) {
            throw new IllegalArgumentException(
                    "El nombre de usuario ya está registrado");
        }

        // 3. Buscar el rol de administrador (debe existir en la tabla roles)
        Rol rolAdmin = rolRepository.buscarPorNombre("ROLE_ADMIN")
                .orElseThrow(() -> new EntityNotFoundException(
                        "Rol de administrador no encontrado"));

        // 4. Guardar el administrador
        Administrador admin = administradorMapper.toEntidad(dto);
        admin.setActivo(true); // cada administrador nuevo nace activo
        Administrador guardado = administradorRepository.save(admin);

        // 5. Crear la cuenta de autenticación
        UsuarioAuth auth = UsuarioAuth.builder()
                .usuario(guardado)
                .user(dto.getUser())
                .pass(passwordEncoder.encode(dto.getPassword()))
                .build();
        usuarioAuthRepository.save(auth);

        // 6. Asignar el rol de administrador
        UsuarioAuthRol asignacion = UsuarioAuthRol.builder()
                .usuarioAuth(auth)
                .rol(rolAdmin)
                .activo(true)
                .fechaAsignacion(LocalDateTime.now())
                .observacion("Rol asignado automáticamente al registrar el administrador")
                .build();
        usuarioAuthRolRepository.save(asignacion);

        return administradorMapper.toDto(guardado);
    }

    // Consultar por id
    @Override
    @Transactional(readOnly = true)
    public AdministradorDto buscarPorId(Long id) {
        return administradorMapper.toDto(buscarEntidad(id));
    }

    // Actualizar datos
    @Override
    @Transactional
    public AdministradorDto actualizar(Long id, AdministradorDto dto) {
        Administrador admin = buscarEntidad(id);
        administradorMapper.actualizarEntidad(dto, admin);
        return administradorMapper.toDto(administradorRepository.save(admin));
    }

    // Activar
    @Override
    @Transactional
    public AdministradorDto activar(Long id) {
        return cambiarEstado(id, true);
    }

    // Desactivar
    @Override
    @Transactional
    public AdministradorDto desactivar(Long id) {
        return cambiarEstado(id, false);
    }

    private AdministradorDto cambiarEstado(Long id, boolean estado) {
        Administrador admin = buscarEntidad(id);
        admin.setActivo(estado);

        // La cuenta de acceso sigue el mismo estado: desactivado = no puede loguearse
        usuarioAuthRepository.findById(id)
                .ifPresent(auth -> auth.setHabilitado(estado));

        return administradorMapper.toDto(administradorRepository.save(admin));
    }

    private Administrador buscarEntidad(Long id) {
        return administradorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontró administrador con id: " + id));
    }
}