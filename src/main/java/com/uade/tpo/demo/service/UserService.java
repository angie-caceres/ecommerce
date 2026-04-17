package com.uade.tpo.demo.service;

import java.util.List;
import java.util.Optional;

import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.exceptions.UserDuplicateException;

public interface UserService {

    public List<User> getUsers();

    public Optional<User> getUserById(Long userId);

    public User createUser(String username, String email, String password)
            throws UserDuplicateException;
    
    public Optional<User> updateUserEmail(Long userId, String newEmail);
}
