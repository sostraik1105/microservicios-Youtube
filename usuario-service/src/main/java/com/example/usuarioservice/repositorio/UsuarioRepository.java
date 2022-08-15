package com.example.usuarioservice.repositorio;

import com.example.usuarioservice.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
