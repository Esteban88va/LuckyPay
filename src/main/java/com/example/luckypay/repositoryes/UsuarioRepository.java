package com.example.luckypay.repositoryes;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.luckypay.models.usuarios.TipoDocumento;
import com.example.luckypay.models.usuarios.Usuario;



public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // metodo para verificar si un usuario ya existe en la base de datos
    // esto es para validar que no se repita el tipo de documento y el numero de
    // documento
    boolean existsByTipoDocAndNumDoc(TipoDocumento tipoDoc, String numDoc);

      // Metodo para buscar un usuario por numero de documento
    Usuario findByNumDoc(String numDoc);
}