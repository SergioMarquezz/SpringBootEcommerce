package com.curso.ecommerce.service;

import com.curso.ecommerce.model.DetalleOrden;
import com.curso.ecommerce.repository.DetalleOrdenDao;
import com.curso.ecommerce.repository.DetalleOrdenJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalleOrdenImplService implements DetalleOrdenDao {

    @Autowired
    private DetalleOrdenJpa detalleOrdenJpa;

    @Override
    public DetalleOrden save(DetalleOrden detalleOrden) {
        return detalleOrdenJpa.save(detalleOrden);
    }
}
