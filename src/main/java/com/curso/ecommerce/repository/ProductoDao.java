package com.curso.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import com.curso.ecommerce.model.Producto;

public interface ProductoDao {
	
	Producto save(Producto producto);
	Optional<Producto> get(Integer id);
	void update(Producto producto);
	void delete(Integer id);
	List<Producto> findAll();
}
