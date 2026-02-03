package com.revplay.service;

import com.revplay.entity.User;

import java.util.Scanner;

public interface UserService {


    void register(String name, String email, String password);

    void register(Scanner sc);
    User login(Scanner sc);

    void registerUser(String name, String email, String password, String role);
    User login(String email, String password);
}
