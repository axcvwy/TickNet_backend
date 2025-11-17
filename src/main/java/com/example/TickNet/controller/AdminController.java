package com.example.TickNet.controller;

import com.example.TickNet.service.ReservationService;
import com.example.TickNet.service.SpectacleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private SpectacleService spectacleService;

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("totalSales", reservationService.getTotalSales());
        model.addAttribute("totalReservations", reservationService.getTotalReservations());
        model.addAttribute("spectacles", spectacleService.getAllSpectacles());
        return "admin/dashboard"; // Assuming a Thymeleaf template
    }
}
