package com.curso.ecommerce.service;

import com.curso.ecommerce.model.Orden;
import com.curso.ecommerce.repository.OrdenDao;
import com.curso.ecommerce.repository.OrdenDaoJpa;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrdenImplService implements OrdenDao {

    private final Logger LOGGER = LoggerFactory.getLogger(OrdenImplService.class);
    @Autowired
    private OrdenDaoJpa jpaOrden;

    @Override
    public List<Orden> listaOrdenes() {
        return jpaOrden.findAll();
    }

    @Override
    public Orden save(Orden orden) {
        return jpaOrden.save(orden);
    }

    @Override
    public String generarNumeroOrden(){
        int numero = 0;
        String numeroConcatenado = "";
        LOGGER.info("LISTA ORDENES {}",listaOrdenes());
        List<Orden> ordenes = listaOrdenes();
        List<Integer> numeros = new ArrayList<Integer>();
        ordenes.stream().forEach(o -> numeros.add(Integer.parseInt(o.getNumero())));

        if(ordenes.isEmpty()){
            numero = 1;
        }
        else{
            numero = numeros.stream().max(Integer::compare).get();
            numero++;
        }

        if(numero < 10){
            numeroConcatenado = "000000000"+String.valueOf(numero);
        }
        else if(numero < 100){
            numeroConcatenado = "00000000"+String.valueOf(numero);
        }
        else if(numero < 1000){
            numeroConcatenado = "0000000"+String.valueOf(numero);
        }
        else if(numero < 10000){
            numeroConcatenado = "0000000"+String.valueOf(numero);
        }
        return numeroConcatenado;
    }
}
