package com.elisa.frontendbackend.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Página de inicio que redirige al login
    @GetMapping("/")
    public String inicio() {
        return "redirect:/login";
    }

    // Página principal tras login exitoso
    @GetMapping("/index")
    public String paginaPrincipal(HttpSession session, Model model) {
        String usuario = (String) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }
        model.addAttribute("usuario", usuario);
        return "index";
    }

    // Cierre de sesión
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
