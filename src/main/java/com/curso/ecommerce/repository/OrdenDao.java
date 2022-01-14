package com.curso.ecommerce.repository;

import com.curso.ecommerce.model.Orden;

import java.util.List;

public interface OrdenDao {

    List<Orden> listaOrdenes();
    Orden save (Orden orden);
    String generarNumeroOrden();
}
