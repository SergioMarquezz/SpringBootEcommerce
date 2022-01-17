package com.curso.ecommerce.controller;

import com.curso.ecommerce.model.DetalleOrden;
import com.curso.ecommerce.model.Orden;
import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.service.DetalleOrdenImplService;
import com.curso.ecommerce.service.OrdenImplService;
import com.curso.ecommerce.service.ProductoImplService;
import com.curso.ecommerce.service.UsuarioImplService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class HomeController {

    private final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private ProductoImplService serviceProducto;
    @Autowired
    private UsuarioImplService usuarioService;
    @Autowired
    private OrdenImplService ordenService;
    @Autowired
    private DetalleOrdenImplService detalleOrdenService;

    //Para almacenar los detalle de la oreden
    private List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();
    //Para guardar los datos de la orden
    private Orden orden = new Orden();

    @GetMapping("")
    public String home(Model model){

        LOGGER.info("ENTRANDO AL INICIO DEL SISTEMA");
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
    public String agregarCarrito(@RequestParam Integer id, @RequestParam Integer cantidad, Model model){
        DetalleOrden ordenDetalle = new DetalleOrden();
        Producto producto = new Producto();
        double sumaTotal = 0;

        Optional<Producto> productoOptional = serviceProducto.get(id);
        LOGGER.info("Producto añadido: {}", productoOptional.get());
        LOGGER.info("Cantidad; {}", cantidad);
        producto = productoOptional.get();
        ordenDetalle.setCantidad(cantidad);
        ordenDetalle.setPrecio(producto.getPrecio());
        ordenDetalle.setNombre(producto.getNombre());
        ordenDetalle.setTotal(producto.getPrecio() * cantidad);
        ordenDetalle.setProducto(producto);

        //Validar que el producto no se agregue dos veces
        Integer idProducto = producto.getId();
        boolean ingresado = detalles.stream().anyMatch(pro -> pro.getProducto().getId() == idProducto);

        if(!ingresado){
            detalles.add(ordenDetalle);
        }
        sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();
        orden.setTotal(sumaTotal);
        model.addAttribute("cart",detalles);
        model.addAttribute("orden",orden);
        return "usuario/carrito";
    }

    //Quitar un producto del carrito
    @GetMapping("delete/cart/{id}")
    public String borrarProductoCarrito(@PathVariable Integer id, Model model){
        //Lista nueva de productos
        List<DetalleOrden> ordenNueva = new ArrayList<DetalleOrden>();
        for(DetalleOrden detalleOrden: detalles){
            if (detalleOrden.getProducto().getId() != id){
                ordenNueva.add(detalleOrden);
            }
        }
        //Poner la nueva lista con los producto restantes
        detalles = ordenNueva;
        double sumaTotal = 0;

        sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();
        orden.setTotal(sumaTotal);
        model.addAttribute("cart",detalles);
        model.addAttribute("orden",orden);

        return "usuario/carrito";
    }
    @GetMapping("/getCart")
    public String getCart(Model model){

        model.addAttribute("cart",detalles);
        model.addAttribute("orden",orden);

        return "/usuario/carrito";
    }

    @GetMapping("/orden")
    public String orden(Model model){

        Usuario user = usuarioService.buscarUsuarioId(1).get();

        model.addAttribute("cart",detalles);
        model.addAttribute("orden",orden);
        model.addAttribute("usuario",user);
        return "usuario/resumenorden";
    }

    //Guardar la orden
    @GetMapping("/saveOrden")
    public String guardarOrden(){
        Date fechaCreacion = new Date();
        orden.setFechaCreacion(fechaCreacion);
        orden.setNumero(ordenService.generarNumeroOrden());

        //Usuario
        Usuario user = usuarioService.buscarUsuarioId(1).get();
        orden.setUsuario(user);
        ordenService.save(orden);

        //Guardar detalles
        for (DetalleOrden dt :detalles){
            dt.setOrden(orden);
            detalleOrdenService.save(dt);
        }
        //limpiar valores lista y detalleOrden
        orden = new Orden();
        detalles.clear();

        return "redirect:/";
    }

    @PostMapping("/search")
    public String searchProducto(@RequestParam String nombre, Model model){
        LOGGER.info("Nombre del producto {} ",nombre);
        List<Producto> productos = serviceProducto.findAll().stream().filter(p -> p.getNombre().contains(nombre)).collect(Collectors.toList());
        model.addAttribute("productos",productos);

        return "usuario/home";
    }
    
}
