package com.example.luckypay.servicesImplement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.luckypay.dtos.UsuarioDto;
import com.example.luckypay.dtos.UsuarioDtoResponse;
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
    public UsuarioDtoResponse guardar(UsuarioDto usuarioDto) {   // ← cambio
        Usuario usuario = usuarioMapper.convierteaEntidad(usuarioDto);
        usuario.setId(null);                                      // paso 2 real
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return usuarioMapper.convierteaDto(usuarioGuardado);
    }

    @Override
public List<UsuarioDtoResponse> listarTodos() {
    return usuarioRepository.findAll()
            .stream()
            .map(usuarioMapper::convierteaDto)
            .toList();
}

    @Override
    public UsuarioDtoResponse buscarPorId(Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public UsuarioDtoResponse buscarPorNumDoc(Long numDoc) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public UsuarioDtoResponse actualizar(Long id, UsuarioDto usuarioDto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void eliminar(Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}