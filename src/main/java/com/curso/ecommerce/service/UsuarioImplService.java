package com.curso.ecommerce.service;

import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.repository.UsuarioDao;
import com.curso.ecommerce.repository.UsuarioDaoJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioImplService implements UsuarioDao {

    @Autowired
    private UsuarioDaoJpa usuarioJpa;

    @Override
    public Optional<Usuario> buscarUsuarioId(Integer id) {
        return usuarioJpa.findById(id);
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        return usuarioJpa.save(usuario);
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioJpa.buscarPorEmail(email);
    }
}
