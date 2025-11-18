package com.example.TickNet.controller;

import com.example.TickNet.entity.Utilisateur;
import com.example.TickNet.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5174")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "user/register"; // Assuming a Thymeleaf template
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute Utilisateur utilisateur) {
        utilisateurService.saveUtilisateur(utilisateur);
        return "redirect:/users/login"; // Redirect to login page after registration
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "user/login"; // Assuming a Thymeleaf template
    }

    // In a real application, Spring Security would handle the POST /login
    // For now, we'll just have a placeholder or rely on simple form submission.
}
