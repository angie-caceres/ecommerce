package com.uade.tpo.demo.service;

import java.util.List;
import java.util.Optional;

import com.uade.tpo.demo.entity.Role;
import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.entity.dto.UserResponse;
import com.uade.tpo.demo.entity.dto.UserRequest;
import com.uade.tpo.demo.exceptions.UserDuplicateException;

public interface UserService {

    public List<UserResponse> getUsers();

    public Optional<User> getUserById(Long userId);

    public User createUser(String email, String password, String firstName, String lastName, Role role)
            throws UserDuplicateException;
    
    public Optional<User> updateUserEmail(Long userId, String newEmail);

    public UserResponse getUserByEmail(String email);

    public UserResponse actualizarUser(String email, UserRequest request);
}