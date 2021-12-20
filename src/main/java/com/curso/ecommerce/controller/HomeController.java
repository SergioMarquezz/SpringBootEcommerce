package com.curso.ecommerce.controller;

import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.service.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class HomeController {

    private final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private ProductoService serviceProducto;

    @GetMapping("")
    public String home(Model model){
        model.addAttribute("productos", serviceProducto.findAll());
        return "usuario/home";
    }

    @GetMapping("homeProducto/{id}")
    public String productoHome(@PathVariable Integer id, Model model){
        LOGGER.info("Id producto enviado como parametro {}",id);
        Producto producto = new Producto();
        Optional<Producto> productoOptional = serviceProducto.get(id);
        producto = productoOptional.get();
        model.addAttribute("product", producto);

        return "usuario/productoHome";
    }

    @PostMapping("/carrito")
    public String agregarCarrito(){
        return "usuario/carrito";
    }
}
