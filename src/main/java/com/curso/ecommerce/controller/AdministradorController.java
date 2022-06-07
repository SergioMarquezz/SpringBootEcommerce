package com.curso.ecommerce.controller;

import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.service.OrdenImplService;
import com.curso.ecommerce.service.ProductoImplService;
import com.curso.ecommerce.service.UsuarioImplService;
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

	@Autowired
	private UsuarioImplService serviceUsuario;

	@Autowired
	private OrdenImplService serviceOrden;

	@GetMapping("")
	public String home(Model model) {

		List<Producto> productoList = productoImplService.findAll();
		model.addAttribute("listaProductos", productoList);
		return "administrador/home";
	}

	@GetMapping("/usuarios")
	public String usuarios(Model model){

		model.addAttribute("usuarios", serviceUsuario.findAll());

		return "administrador/usuarios";
	}

	@GetMapping("/ordenes")
	public String ordenes(Model model){

		model.addAttribute("ordenes",serviceOrden.listaOrdenes());
		return "administrador/ordenes";
	}
}
