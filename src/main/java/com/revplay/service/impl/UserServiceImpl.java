package com.revplay.service.impl;

import com.revplay.entity.User;
import com.revplay.exception.AuthenticationException;
import com.revplay.repository.UserRepository;
import com.revplay.service.UserService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepo,
                           PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    // ============================
    // CONSOLE METHODS
    // ============================

    @Override
    public void register(String name, String email, String password) {

    }

    @Override
    public void register(Scanner sc) {
        System.out.print("Name: ");
        String name = sc.next();

        System.out.print("Email: ");
        String email = sc.next();

        System.out.print("Password: ");
        String password = sc.next();

        registerUser(name, email, password, "USER");
    }

    @Override
    public User login(Scanner sc) {
        System.out.print("Email: ");
        String email = sc.next();

        System.out.print("Password: ");
        String password = sc.next();

        return login(email, password);
    }

    // ============================
    // CORE BUSINESS METHODS
    // ============================

    @Override
    public void registerUser(String name,
                             String email,
                             String password,
                             String role) {

        if (userRepo.findByEmail(email).isPresent()) {
            throw new AuthenticationException("Email already exists");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);

        userRepo.save(user);

        log.info("✅ User registered: {}", email);
    }

    @Override
    public User login(String email, String password) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new AuthenticationException("Invalid email"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthenticationException("Invalid password");
        }

        log.info("✅ User logged in successfully: {}", email);
        return user;
    }
}
