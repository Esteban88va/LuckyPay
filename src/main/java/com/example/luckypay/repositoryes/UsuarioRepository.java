package com.example.luckypay.repositoryes;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.luckypay.models.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
