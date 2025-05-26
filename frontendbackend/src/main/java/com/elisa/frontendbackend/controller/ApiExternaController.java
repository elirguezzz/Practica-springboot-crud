package com.ejemplo.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Controller
public class ApiExternaController {

    private final String URL_API = "http://localhost:5000/pokeapi";

    @GetMapping("/apiexterna")
    public String mostrarFormulario() {
        return "apiexterna";
    }

    @PostMapping("/apiexterna")
    public String consultarPokemon(@RequestParam String nombre, Model model) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = URL_API + "?nombre=" + nombre;
            Map respuesta = restTemplate.getForObject(url, Map.class);

            if (respuesta.containsKey("error")) {
                model.addAttribute("error", respuesta.get("error"));
            } else {
                model.addAttribute("pokemon", respuesta);
            }

        } catch (Exception e) {
            model.addAttribute("error", "Error al consultar la API externa: " + e.getMessage());
        }

        return "apiexterna";
    }
}

