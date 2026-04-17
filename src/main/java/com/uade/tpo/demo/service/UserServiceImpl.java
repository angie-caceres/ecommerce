package com.uade.tpo.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import com.uade.tpo.demo.entity.Carrito;
import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.exceptions.UserDuplicateException;
import com.uade.tpo.demo.repository.CarritoRepository;
import com.uade.tpo.demo.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarritoRepository carritoRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    
    @Transactional
    public User createUser(String username, String email, String password)
        throws UserDuplicateException {
    if (userRepository.existsByUsername(username))
        throw new UserDuplicateException();
    
    User user = userRepository.save(new User(username, email, password));
    Carrito carrito = new Carrito(user, LocalDateTime.now(), "ACTIVO", 0);
    carritoRepository.save(carrito);
    
    return user;
}
    
    public Optional<User> updateUserEmail(Long userId, String newEmail) {
        return userRepository.findById(userId).map(user -> {
            user.setEmail(newEmail);
        return userRepository.save(user);
        });
    }
}