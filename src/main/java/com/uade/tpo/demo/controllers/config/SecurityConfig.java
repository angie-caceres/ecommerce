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
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/error").permitAll()

                .requestMatchers(HttpMethod.GET, "/libros/todos").hasRole("ADMINISTRADOR")
                .requestMatchers(HttpMethod.GET, "/libros/*/admin").hasRole("ADMINISTRADOR")
                .requestMatchers(HttpMethod.GET, "/libros/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/libros/**").hasRole("ADMINISTRADOR")
                .requestMatchers(HttpMethod.PATCH, "/libros/**").hasRole("ADMINISTRADOR")
                .requestMatchers(HttpMethod.DELETE, "/libros/**").hasRole("ADMINISTRADOR")

                .requestMatchers("/carrito/**").hasRole("USER")

                .requestMatchers(HttpMethod.GET, "/ordenes/usuario/me").hasRole("USER")
                .requestMatchers(HttpMethod.GET, "/ordenes/*").hasAnyRole("USER", "ADMINISTRADOR")
                .requestMatchers(HttpMethod.GET, "/ordenes/**").hasRole("ADMINISTRADOR")
                .requestMatchers(HttpMethod.PATCH, "/ordenes/*/cancelar").hasRole("ADMINISTRADOR")

                .requestMatchers(HttpMethod.GET, "/generos/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/generos/**").hasRole("ADMINISTRADOR")
                .requestMatchers(HttpMethod.DELETE, "/generos/**").hasRole("ADMINISTRADOR")
                .requestMatchers(HttpMethod.PATCH, "/generos/**").hasRole("ADMINISTRADOR")

                .requestMatchers(HttpMethod.GET, "/editoriales/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/editoriales/**").hasRole("ADMINISTRADOR")
                .requestMatchers(HttpMethod.DELETE, "/editoriales/**").hasRole("ADMINISTRADOR")
                .requestMatchers(HttpMethod.PATCH, "/editoriales/**").hasRole("ADMINISTRADOR")

                .requestMatchers("/descuentos/**").hasRole("ADMINISTRADOR")
                .requestMatchers("/imagenes/**").hasRole("ADMINISTRADOR")
                .requestMatchers(HttpMethod.GET, "/autores/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/autores").authenticated()
                .requestMatchers(HttpMethod.PATCH, "/autores/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/autores/**").authenticated()

                .requestMatchers(HttpMethod.GET, "/usuarios/me").hasAnyRole("USER", "ADMINISTRADOR")
                .requestMatchers(HttpMethod.PATCH, "/usuarios/me").hasAnyRole("USER", "ADMINISTRADOR")
                .requestMatchers(HttpMethod.GET, "/usuarios").hasRole("ADMINISTRADOR")
                .requestMatchers(HttpMethod.PATCH, "/usuarios/*/activo").hasRole("ADMINISTRADOR")

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