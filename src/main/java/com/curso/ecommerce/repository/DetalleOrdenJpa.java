package com.curso.ecommerce.repository;

import com.curso.ecommerce.model.DetalleOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleOrdenJpa extends JpaRepository<DetalleOrden,Integer> {
}
