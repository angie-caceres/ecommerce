package com.uade.tpo.demo.controllers.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(req -> req
                // Auth - público
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/error").permitAll()

                // Libros - ver: ambos roles
                .requestMatchers(HttpMethod.GET, "/libros/**").permitAll()
                // Libros - crear/modificar/eliminar: solo ADMIN
                .requestMatchers(HttpMethod.POST, "/libros/**").hasRole("ADMINISTRADOR")
                .requestMatchers(HttpMethod.PATCH, "/libros/**").hasRole("ADMINISTRADOR")
                .requestMatchers(HttpMethod.DELETE, "/libros/**").hasRole("ADMINISTRADOR")

                // Carrito - solo USER
                .requestMatchers("/carrito/**").hasRole("USER")

                // Ordenes - ver propias: USER, ver todas: ADMIN
                .requestMatchers(HttpMethod.GET, "/ordenes/usuario/me**").hasRole("USER")
                .requestMatchers(HttpMethod.GET, "/ordenes/**").hasRole("ADMINISTRADOR")

                // Géneros
                .requestMatchers(HttpMethod.GET, "/generos/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/generos/**").hasRole("ADMINISTRADOR")
                .requestMatchers(HttpMethod.DELETE, "/generos/**").hasRole("ADMINISTRADOR")
                .requestMatchers(HttpMethod.PATCH, "/generos/**").hasRole("ADMINISTRADOR")

                // Editoriales
                .requestMatchers(HttpMethod.GET, "/editoriales/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/editoriales/**").hasRole("ADMINISTRADOR")
                .requestMatchers(HttpMethod.DELETE, "/editoriales/**").hasRole("ADMINISTRADOR")
                .requestMatchers(HttpMethod.PATCH, "/editoriales/**").hasRole("ADMINISTRADOR")

                // Descuentos/imagen
                .requestMatchers(HttpMethod.GET, "/descuentos/**").hasRole("ADMINISTRADOR")
                .requestMatchers(HttpMethod.POST, "/descuentos/**").hasRole("ADMINISTRADOR")
                .requestMatchers(HttpMethod.PATCH, "/descuentos/**").hasRole("ADMINISTRADOR")
                .requestMatchers("/imagenes/**").hasRole("ADMINISTRADOR")

                // Autores - solo ADMIN
                .requestMatchers("/autores/**").hasRole("ADMINISTRADOR")
                .requestMatchers(HttpMethod.DELETE, "/autores/**").hasRole("ADMINISTRADOR")
                .requestMatchers(HttpMethod.PATCH, "/autores/**").hasRole("ADMINISTRADOR")

                // Usuarios
                .requestMatchers(HttpMethod.GET, "/api/v1/users/me").hasAnyRole("USER", "ADMINISTRADOR")
                .requestMatchers(HttpMethod.PATCH, "/api/v1/users/me").hasAnyRole("USER", "ADMINISTRADOR")
                .requestMatchers(HttpMethod.GET, "/api/v1/users").hasRole("ADMINISTRADOR")

                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
