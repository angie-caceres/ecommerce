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
                // Auth - público
                .requestMatchers("/api/v1/auth/**").permitAll()
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
                .requestMatchers(HttpMethod.GET, "/ordenes/usuario/me**").hasRole("USER")//solo ve las propias
                .requestMatchers(HttpMethod.GET, "/ordenes/*").hasAnyRole("USER", "ADMINISTRADOR")
                .requestMatchers(HttpMethod.GET, "/ordenes/**").hasRole("ADMINISTRADOR")//ve ordendes por id de user, id de orden y todas

                // Géneros
                .requestMatchers(HttpMethod.GET, "/generos/**").hasAnyRole("ADMINISTRADOR")
                .requestMatchers(HttpMethod.POST, "/generos/**").hasRole("ADMINISTRADOR")
                .requestMatchers(HttpMethod.DELETE, "/generos/**").hasRole("ADMINISTRADOR")
                .requestMatchers(HttpMethod.PATCH, "/generos/**").hasRole("ADMINISTRADOR")

                // Editoriales
                .requestMatchers(HttpMethod.GET, "/editoriales/**").hasAnyRole("ADMINISTRADOR")
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
                

                //Cada usuario puede ver sus datos y actualizarlos (menos el rol). El ADMINISTADOR puede ver todos los usuarios
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
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}