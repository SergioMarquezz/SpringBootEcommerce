package com.curso.ecommerce.repository;

import java.util.Optional;

import com.curso.ecommerce.model.Producto;

public interface ProductoDao {
	
	public Producto save(Producto producto);
	public Optional<Producto> get(Integer id);
	public void update(Producto producto);
	public void delete(Integer id);
}
