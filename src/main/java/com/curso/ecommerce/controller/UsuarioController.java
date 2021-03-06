package com.curso.ecommerce.controller;

import com.curso.ecommerce.model.Orden;
import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.service.OrdenImplService;
import com.curso.ecommerce.service.UsuarioImplService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final Logger LOGGER = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioImplService serviceUsuario;

    @Autowired
    private OrdenImplService serviceOrden;

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

    @GetMapping("/compras")
    public String obtenerCompras(Model model, HttpSession session){

        model.addAttribute("sesion",session.getAttribute("idUsuario"));
        Integer idUsuario = Integer.parseInt(session.getAttribute("idUsuario").toString());
        Usuario usuario = serviceUsuario.buscarUsuarioId(idUsuario).get();
        List<Orden> ordenes = serviceOrden.findByUsuario(usuario);

        model.addAttribute("ordenes", ordenes);
        return "usuario/compras";
    }

    @GetMapping("/detalle/{id}")
    public String detalleOrdenUsuario(@PathVariable Integer id, HttpSession sesion, Model model){

        LOGGER.info("Id de la orden {}", id);
        Optional<Orden> orden = serviceOrden.ordenPorID(id);

        model.addAttribute("detalles", orden.get().getDetalle());

        model.addAttribute("sesion",sesion.getAttribute("idUsuario"));

        return "usuario/detallecompra";
    }

    @GetMapping("/cerrar")
    public String cerrarSession(HttpSession session){

        session.removeAttribute("idUsuario");

        return "redirect:/";
    }
}
