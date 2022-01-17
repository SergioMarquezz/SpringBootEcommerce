package com.curso.ecommerce.controller;

import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.service.UsuarioImplService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final Logger LOGGER = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioImplService serviceUsuario;

    @GetMapping("/login")
    public String login(){
        return "usuario/login";
    }

    @GetMapping("/registro")
    public String crear(){
        return "usuario/registro";
    }

    @PostMapping("/save")
    public String save(Usuario user){

        LOGGER.info("USUARIO REGISTRADO {}",user);
        user.setTipo("USER");
        serviceUsuario.guardar(user);

        return "redirect:/";
    }
}
