package com.elisa.frontendbackend.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.client.RestTemplate;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String mostrarFormularioLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String usuario,
                                @RequestParam String contrasena,
                                HttpSession session,
                                Model model) {
        // Petición a la API Flask
        String url = "http://localhost:5000/login";
        Map<String, String> datos = new HashMap<>();
        datos.put("usuario", usuario);
        datos.put("contrasena", contrasena);

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> respuesta = restTemplate.postForObject(url, datos, Map.class);

        if (respuesta.containsKey("mensaje")) {
            session.setAttribute("usuario", usuario);
            model.addAttribute("usuario", usuario);
            return "index"; // Thymeleaf renderiza index.html con el nombre
        } else {
            model.addAttribute("error", respuesta.get("error"));
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // elimina todos los atributos de sesión
        return "redirect:/login"; // redirige al formulario de login
    }

}
