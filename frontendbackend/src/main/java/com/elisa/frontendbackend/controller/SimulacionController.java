package com.elisa.frontendbackend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Controller
public class SimulacionController {

    @GetMapping("/simulacion")
    public String mostrarSimulacion(Model model) {
        String url = "http://localhost:5000/simulacion";  // Asegúrate de tener este endpoint en Flask
        RestTemplate restTemplate = new RestTemplate();

        try {
            Map<String, Object> respuesta = restTemplate.getForObject(url, Map.class);
            model.addAttribute("resultado", respuesta.get("resultado"));
        } catch (Exception e) {
            model.addAttribute("resultado", "Error al contactar con la API Flask");
        }

        return "simulacion";
    }
}

