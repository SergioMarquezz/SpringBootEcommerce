package com.curso.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.repository.ProductoDao;
import com.curso.ecommerce.repository.ProductoDaoJpa;

@Service
public class ProductoImplService implements ProductoDao{

	@Autowired
	private ProductoDaoJpa daoJpa;
	
	@Override
	public Producto save(Producto producto) {
		
		return daoJpa.save(producto);
	}

	@Override
	public Optional<Producto> get(Integer id) {
		
		return daoJpa.findById(id);
	}

	@Override
	public void update(Producto producto) {
		
		daoJpa.save(producto);
		
	}

	@Override
	public void delete(Integer id) {
		
		daoJpa.deleteById(id);
	}

	@Override
	public List<Producto> findAll() {
		
		return daoJpa.findAll();
	}

}
