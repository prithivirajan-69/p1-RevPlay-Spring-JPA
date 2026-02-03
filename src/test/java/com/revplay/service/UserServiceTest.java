package com.revplay.service;

import com.revplay.entity.User;
import com.revplay.exception.AuthenticationException;
import com.revplay.repository.UserRepository;
import com.revplay.service.impl.UserServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_success() {
        when(userRepo.findByEmail("test@mail.com"))
                .thenReturn(Optional.empty());
        when(passwordEncoder.encode("1234"))
                .thenReturn("hashed");

        userService.registerUser("Test", "test@mail.com", "1234", "USER");

        verify(userRepo, times(1)).save(any(User.class));
    }

    @Test
    void registerUser_emailAlreadyExists() {
        when(userRepo.findByEmail("test@mail.com"))
                .thenReturn(Optional.of(new User()));

        assertThrows(AuthenticationException.class, () ->
                userService.registerUser("Test", "test@mail.com", "1234", "USER"));
    }

    @Test
    void login_success() {
        User user = new User();
        user.setPassword("hashed");

        when(userRepo.findByEmail("test@mail.com"))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches("1234", "hashed"))
                .thenReturn(true);

        User logged = userService.login("test@mail.com", "1234");

        assertNotNull(logged);
    }

    @Test
    void login_wrongPassword() {
        User user = new User();
        user.setPassword("hashed");

        when(userRepo.findByEmail("test@mail.com"))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "hashed"))
                .thenReturn(false);

        assertThrows(AuthenticationException.class, () ->
                userService.login("test@mail.com", "wrong"));
    }
}
