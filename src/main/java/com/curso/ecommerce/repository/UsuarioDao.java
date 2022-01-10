package com.curso.ecommerce.repository;

import com.curso.ecommerce.model.Usuario;

import java.util.Optional;

public interface UsuarioDao {

    Optional<Usuario> buscarUsuarioId(Integer id);
}
