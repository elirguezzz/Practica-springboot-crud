package com.elisa.frontendbackend.controller;

import com.elisa.frontendbackend.entity.Categoria;
import com.elisa.frontendbackend.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public String listarCategorias(Model model) {
        List<Categoria> categorias = categoriaRepository.findAll();
        model.addAttribute("categorias", categorias);
        return "categorias/lista";
    }

    @GetMapping("/nueva")
    public String nuevaCategoriaForm(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categorias/formulario";
    }

    @PostMapping("/guardar")
    public String guardarCategoria(@ModelAttribute Categoria categoria) {
        categoriaRepository.save(categoria);
        return "redirect:/categorias";
    }

    @GetMapping("/editar/{id}")
    public String editarCategoria(@PathVariable Long id, Model model) {
        Categoria categoria = categoriaRepository.findById(id).orElse(null);
        model.addAttribute("categoria", categoria);
        return "categorias/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCategoria(@PathVariable Long id) {
        categoriaRepository.deleteById(id);
        return "redirect:/categorias";
    }
}
