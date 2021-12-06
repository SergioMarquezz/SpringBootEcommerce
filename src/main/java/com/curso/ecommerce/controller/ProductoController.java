package com.curso.ecommerce.controller;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.service.ProductoService;
import com.curso.ecommerce.service.UploadFileService;

@Controller
@RequestMapping("/productos")
public class ProductoController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);
	
	@Autowired
	private ProductoService serviceProducto;
	
	@Autowired
	private UploadFileService upload;
	
	@GetMapping("")
	public String show(Model model) {
		
		model.addAttribute("productos", serviceProducto.findAll());
		return "productos/show";
	}
	
	@GetMapping("/create")
	public String create() {
		
		return "productos/create";
	}
	
	@PostMapping("/save")
	public String save(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {
		
		LOGGER.info("Objeto del producto {}",producto);
		Usuario user = new Usuario(1,"","","","","","","");
		producto.setUsuario(user);
		
		//imagen
		if(producto.getId() == null) { //cuando se crea un producto
			
			String nombreImagen = upload.saveImg(file);
			producto.setImagen(nombreImagen);
		}
		else {
			if(file.isEmpty()) {//Cuando se edita el producto pero no se cambia la imagen
				Producto pro = new Producto();
				pro = serviceProducto.get(producto.getId()).get();
				producto.setImagen(pro.getImagen());
			}
			
			else {
				String nombreImagen = upload.saveImg(file);
				producto.setImagen(nombreImagen);
			}
		}
		
		serviceProducto.save(producto);
		
		return "redirect:/productos";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Integer id, Model model) {
		
		Producto producto = new Producto();
		Optional<Producto> productoOptional = serviceProducto.get(id);
		producto = productoOptional.get();
		
		LOGGER.info("Producto buscado {} ",producto);
		model.addAttribute("producto", producto);
		
		return "productos/edit";
	}
	
	@PostMapping("/update")
	public String update(Producto producto) {
		
		serviceProducto.update(producto);
		return "redirect:/productos";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id) {
		
		serviceProducto.delete(id);
		
		return "redirect:/productos";
	}
}