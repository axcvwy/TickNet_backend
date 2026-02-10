package com.example.TickNet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; // Import this

import com.example.TickNet.config.CustomOAuth2SuccessHandler;

@Configuration
public class SecurityConfig {

        @Autowired
        private CustomOAuth2SuccessHandler customOAuth2SuccessHandler;

        @Autowired
        private JwtAuthenticationFilter jwtAuthenticationFilter; // Inject Filter

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(AbstractHttpConfigurer::disable)
                                .cors(Customizer.withDefaults())
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/api/auth/**", "/api/spectacles/**",
                                                                "/api/sessions/**", "/api/sessions/spectacle/**",
                                                                "/service/stripe/**")
                                                .permitAll()
                                                .requestMatchers("/api/users/auth/**").permitAll() // Allow User Auth
                                                .anyRequest().authenticated())
                                .oauth2Login(oauth2 -> oauth2
                                                .successHandler(customOAuth2SuccessHandler))
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Add
                                                                                                                       // Filter

                return http.build();
        }
}
