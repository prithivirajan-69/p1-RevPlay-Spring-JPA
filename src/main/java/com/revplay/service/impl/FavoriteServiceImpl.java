package com.revplay.service.impl;

import com.revplay.entity.Song;
import com.revplay.entity.User;
import com.revplay.repository.SongRepository;
import com.revplay.repository.UserRepository;
import com.revplay.service.FavoriteService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Scanner;

@Service
@Transactional
public class FavoriteServiceImpl implements FavoriteService {

    private final UserRepository userRepo;
    private final SongRepository songRepo;

    public FavoriteServiceImpl(UserRepository userRepo, SongRepository songRepo) {
        this.userRepo = userRepo;
        this.songRepo = songRepo;
    }

    // ===============================
    // 🎧 CONSOLE METHOD
    // ===============================
    @Override
    public void addFavorite(Scanner sc, User user) {

        System.out.print("Enter Song ID to add to favorites: ");
        Long songId = sc.nextLong();

        addToFavorites(user.getId(), songId);
    }

    // ===============================
    // ❤️ CORE BUSINESS LOGIC
    // ===============================
    @Override
    public void addToFavorites(Long userId, Long songId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Song song = songRepo.findById(songId)
                .orElseThrow(() -> new RuntimeException("Song not found"));

        user.getFavorites().add(song);
        userRepo.save(user);

        System.out.println("❤️ Added to favorites: " + song.getTitle());
    }

    @Override
    public void viewFavorites(Long userId) {

    }

    // ===============================
    // 📃 VIEW FAVORITES
    // ===============================
    @Override
    public void viewFavoriteStats(Long userId) {

        User user = userRepo.findById(userId).orElseThrow();

        System.out.println("❤️ Favorite Statistics");

        System.out.println("Total favorites: " + user.getFavorites().size());

        user.getFavorites().stream()
                .collect(java.util.stream.Collectors.groupingBy(Song::getGenre))
                .forEach((genre, songs) ->
                        System.out.println("Genre: " + genre + " → " + songs.size()));
    }

}
