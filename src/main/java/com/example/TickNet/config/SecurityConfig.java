package com.example.TickNet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // API stateless (pas de session)
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())

                // autoriser l'accès à tes endpoints API pour le dev
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/**"
                        ).permitAll()
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}
