package com.example.TickNet.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "nom obligatoire")
        String nom,

        @Email(message = "email invalide")
        @NotBlank(message = "email obligatoire")
        String email,

        @NotBlank(message = "mot de passe obligatoire")
        @Size(min = 6, message = "mot de passe >= 6 caractères")
        String motDePasse,

        String telephone
) {}
