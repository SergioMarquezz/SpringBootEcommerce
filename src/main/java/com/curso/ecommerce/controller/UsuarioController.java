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

import javax.servlet.http.HttpSession;
import java.util.Optional;

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

    @PostMapping("/acceder")
    public String acceder(Usuario user, HttpSession session){
        LOGGER.info("ACCESOS : {}",user);
        String vista = "";
        Optional<Usuario> usuario = serviceUsuario.buscarPorEmail(user.getEmail());
        if (usuario.isPresent()){
            session.setAttribute("idUsuario",usuario.get().getId());
            if (usuario.get().getTipo().equals("ADMIN")){
                vista = "redirect:/administrador";
            }
            else{
                vista = "redirect:/";
            }
        }
        else{
            LOGGER.info("EL USUARIO NO EXISTE");
        }
        return vista;
    }
}
