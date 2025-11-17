package com.example.TickNet.controller;

import com.example.TickNet.entity.Spectacle;
import com.example.TickNet.service.SpectacleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/spectacles")
public class SpectacleController {

    @Autowired
    private SpectacleService spectacleService;

    @GetMapping
    public String listSpectacles(Model model) {
        List<Spectacle> spectacles = spectacleService.getAllSpectacles();
        model.addAttribute("spectacles", spectacles);
        return "spectacle/list"; // This assumes you have a Thymeleaf template at src/main/resources/templates/spectacle/list.html
    }

    @GetMapping("/{id}")
    public String viewSpectacle(@PathVariable Long id, Model model) {
        Spectacle spectacle = spectacleService.getSpectacleById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid spectacle Id:" + id));
        model.addAttribute("spectacle", spectacle);
        return "spectacle/detail"; // This assumes you have a Thymeleaf template at src/main/resources/templates/spectacle/detail.html
    }

    // Admin functionalities
    @GetMapping("/admin/new")
    public String showCreateForm(Model model) {
        model.addAttribute("spectacle", new Spectacle());
        return "spectacle/form";
    }

    @PostMapping("/admin")
    public String saveSpectacle(@ModelAttribute Spectacle spectacle) {
        spectacleService.saveSpectacle(spectacle);
        return "redirect:/spectacles";
    }

    @GetMapping("/admin/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Spectacle spectacle = spectacleService.getSpectacleById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid spectacle Id:" + id));
        model.addAttribute("spectacle", spectacle);
        return "spectacle/form";
    }

    @GetMapping("/admin/manage-sessions/{id}")
    public String manageSessions(@PathVariable Long id) {
        return "redirect:/admin/sessions/spectacle/" + id;
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteSpectacle(@PathVariable Long id) {
        spectacleService.deleteSpectacle(id);
        return "redirect:/spectacles";
    }
}
