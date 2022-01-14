package com.curso.ecommerce.repository;

import com.curso.ecommerce.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenDaoJpa extends JpaRepository<Orden,Integer> {
}
