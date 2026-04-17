package com.uade.tpo.demo.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.uade.tpo.demo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // para login
    Optional<User> findByEmail(String email);

    // para verificar duplicado al registrar
    Optional<User> findByUsername(String username);

    // para verificar si el email ya existe
    boolean existsByEmail(String email);

    // para verificar si el username ya existe
    boolean existsByUsername(String username);
}