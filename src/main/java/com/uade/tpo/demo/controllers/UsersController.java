package com.uade.tpo.demo.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.entity.dto.UserRequest;
import com.uade.tpo.demo.entity.dto.UserResponse;
import com.uade.tpo.demo.controllers.auth.AuthenticationRequest;
import com.uade.tpo.demo.controllers.auth.AuthenticationResponse;
import com.uade.tpo.demo.service.AuthenticationService;
import com.uade.tpo.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    // el admin puede ver a todos los usuarios
    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }


    @GetMapping("/me")
    public ResponseEntity<UserResponse> getUserByEmail(Authentication auth) {
        UserResponse user = userService.getUserByEmail(auth.getName());
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/me")
    public ResponseEntity<?> actualizarUser(
            Authentication auth,
            @RequestBody UserRequest request) {
        UserResponse actualizado = userService.actualizarUser(auth.getName(), request);
        if (request.getPassword() != null) {
            AuthenticationRequest authRequest = new AuthenticationRequest(actualizado.getEmail(), request.getPassword());
            return ResponseEntity.ok(authenticationService.authenticate(authRequest));
        }
        return ResponseEntity.ok(actualizado); //si cambia la passw por un null no se actualiza
    }
}