package com.revplay.service.impl;

import com.revplay.entity.Playlist;
import com.revplay.entity.Song;
import com.revplay.entity.User;
import com.revplay.repository.PlaylistRepository;
import com.revplay.repository.SongRepository;
import com.revplay.repository.UserRepository;
import com.revplay.service.PlaylistService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Scanner;

@Service
@Transactional
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepo;
    private final SongRepository songRepo;
    private final UserRepository userRepo;

    public PlaylistServiceImpl(
            PlaylistRepository playlistRepo,
            SongRepository songRepo,
            UserRepository userRepo) {

        this.playlistRepo = playlistRepo;
        this.songRepo = songRepo;
        this.userRepo = userRepo;
    }

    // ===============================
    // CORE LOGIC (Controller)
    // ===============================
    @Override
    public Playlist createPlaylist(Long userId, String name, String description, boolean isPublic) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Playlist playlist = new Playlist();
        playlist.setName(name);
        playlist.setDescription(description);
        playlist.setPublic(isPublic);
        playlist.setUser(user);

        return playlistRepo.save(playlist);
    }

    @Override
    public void addSongToPlaylist(Long playlistId, Long songId) {

        Playlist playlist = playlistRepo.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        Song song = songRepo.findById(songId)
                .orElseThrow(() -> new RuntimeException("Song not found"));

        playlist.getSongs().add(song);
        playlistRepo.save(playlist);
    }

    @Override
    public List<Playlist> getUserPlaylists(Long userId) {
        return playlistRepo.findByUserId(userId);
    }

    // ===============================
    // CONSOLE METHODS
    // ===============================
    @Override
    public void createPlaylistConsole(Scanner sc, Long userId) {

        System.out.print("Playlist name: ");
        String name = sc.next();

        System.out.print("Description: ");
        String desc = sc.next();

        System.out.print("Public? (true/false): ");
        boolean isPublic = sc.nextBoolean();

        createPlaylist(userId, name, desc, isPublic);
        System.out.println("✅ Playlist created");
    }

    @Override
    public void addSongConsole(Scanner sc, User loggedInUser) {

        System.out.print("Playlist ID: ");
        Long playlistId = sc.nextLong();

        System.out.print("Song ID: ");
        Long songId = sc.nextLong();

        addSongToPlaylist(playlistId, songId);
        System.out.println("🎵 Song added to playlist");
    }

    @Override
    public void viewUserPlaylists(Long userId) {

        List<Playlist> playlists = getUserPlaylists(userId);

        if (playlists.isEmpty()) {
            System.out.println("No playlists found.");
            return;
        }

        playlists.forEach(p ->
                System.out.println("📀 " + p.getId() + " - " + p.getName()));
    }
}

