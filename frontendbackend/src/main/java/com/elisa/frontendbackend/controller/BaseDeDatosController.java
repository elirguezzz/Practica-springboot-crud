package com.elisa.frontendbackend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Controller
public class BaseDeDatosController {

    private final String URL_USUARIOS = "http://localhost:5000/usuarios";

    @GetMapping("/api")
    public String obtenerUsuarios(Model model) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            List<Map<String, Object>> usuarios = restTemplate.getForObject(URL_USUARIOS, List.class);
            model.addAttribute("usuarios", usuarios);
        } catch (Exception e) {
            model.addAttribute("error", "Error al consultar usuarios: " + e.getMessage());
        }
        return "usuarios";
    }
}
