package com.uade.tpo.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.Role;
import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.entity.dto.UserRequest;
import com.uade.tpo.demo.entity.dto.UserResponse;
import com.uade.tpo.demo.exceptions.UserDuplicateException;
import com.uade.tpo.demo.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserResponse> getUsers() {
        return userRepository.findAll()
            .stream()
            .map(user -> UserResponse.builder()
                .idUsuario(user.getIdUsuario())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .build())
            .collect(Collectors.toList());
    }

    
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }


    public Optional<User> updateUserEmail(Long userId, String newEmail) {
        return userRepository.findById(userId).map(user -> {
            user.setEmail(newEmail);
            return userRepository.save(user);
        });
    }

    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return UserResponse.builder()
                .idUsuario(user.getIdUsuario())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .build();
    }

    public UserResponse actualizarUser(String email, UserRequest request) {
        User user = userRepository.findByEmail(email).orElseThrow();
        
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getPassword() != null) user.setPassword(passwordEncoder.encode(request.getPassword()));
        if (request.getFirstName() != null) user.setFirstName(request.getFirstName());
        if (request.getLastName() != null) user.setLastName(request.getLastName());
        
        userRepository.save(user);
        return getUserByEmail(user.getEmail());
    }

}