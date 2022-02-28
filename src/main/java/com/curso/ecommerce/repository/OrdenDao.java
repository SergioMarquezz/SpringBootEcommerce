package com.curso.ecommerce.repository;

import com.curso.ecommerce.model.Orden;
import com.curso.ecommerce.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface OrdenDao {

    List<Orden> listaOrdenes();
    Optional<Orden> ordenPorID(Integer id);
    Orden save (Orden orden);
    String generarNumeroOrden();
    List<Orden> findByUsuario (Usuario user);
}
