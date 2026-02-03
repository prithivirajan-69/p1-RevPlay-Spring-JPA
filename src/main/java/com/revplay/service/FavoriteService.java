package com.revplay.service;

import com.revplay.entity.User;

import java.util.Scanner;

public interface FavoriteService {
    void addFavorite(Scanner sc, User user);

    void addToFavorites(Long userId, Long songId);
    void viewFavorites(Long userId);

    // ===============================
    // 📃 VIEW FAVORITES
    // ===============================
    void viewFavoriteStats(Long userId);
}
