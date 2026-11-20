package com.example.luckypay.servicesImplement;

import org.springframework.stereotype.Service;

import com.example.luckypay.dtos.UsuarioDto;
import com.example.luckypay.mappers.UsuarioMapper;
import com.example.luckypay.models.usuarios.Usuario;
import com.example.luckypay.repositoryes.UsuarioRepository;
import com.example.luckypay.services.UsuarioService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public UsuarioDto guardar(UsuarioDto usuarioDto) {
        // 1. Convertir DTO a entidad
        Usuario usuario = usuarioMapper.convierteaEntidad(usuarioDto);

        // 2. Asegurar que el ID sea nulo para que se genere uno nuevo

        // 3. Guardar en la base de datos
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // 4. Convertir la entidad guardada a DTO y devolverla
        return usuarioMapper.convierteaDto(usuarioGuardado);
    }
}