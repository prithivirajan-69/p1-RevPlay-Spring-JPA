package com.revplay.controller;

import com.revplay.entity.User;
import com.revplay.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ===============================
    // 🔐 REGISTER
    // ===============================
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {

        userService.register(
                user.getName(),
                user.getEmail(),
                user.getPassword()
        );

        return ResponseEntity.ok("✅ User registered successfully");
    }

    // ===============================
    // 🔑 LOGIN
    // ===============================
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {

        User loggedInUser = userService.login(
                user.getEmail(),
                user.getPassword()
        );

        return ResponseEntity.ok(loggedInUser);
    }
}
