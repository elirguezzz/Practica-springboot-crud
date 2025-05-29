package com.elisa.frontendbackend.controller;

import com.elisa.frontendbackend.entity.Producto;
import com.elisa.frontendbackend.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    // Mostrar lista de productos
    @GetMapping
    public String listarProductos(Model model) {
        model.addAttribute("productos", productoRepository.findAll());
        return "productos/lista";
    }

    // Mostrar formulario para crear producto
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("producto", new Producto());
        return "productos/formulario";
    }

    // Guardar producto nuevo o editado
    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute Producto producto) {
        productoRepository.save(producto);
        return "redirect:/productos";
    }

    // Mostrar formulario para editar producto existente
    @GetMapping("/editar/{id}")
    public String editarProducto(@PathVariable Long id, Model model) {
        Producto producto = productoRepository.findById(id).orElse(null);
        model.addAttribute("producto", producto);
        return "productos/formulario";
    }

    // Eliminar producto
    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productoRepository.deleteById(id);
        return "redirect:/productos";
    }
}
