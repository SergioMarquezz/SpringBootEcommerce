package com.curso.ecommerce.controller;

import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.service.ProductoImplService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {

	@Autowired
	private ProductoImplService productoImplService;

	@GetMapping("")
	public String home(Model model) {

		List<Producto> productoList = productoImplService.findAll();
		model.addAttribute("listaProductos", productoList);
		return "administrador/home";
	}
}
