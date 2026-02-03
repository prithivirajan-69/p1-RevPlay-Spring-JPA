package com.revplay.service.impl;

import com.revplay.entity.Artist;
import com.revplay.entity.Song;
import com.revplay.entity.User;
import com.revplay.repository.ArtistRepository;
import com.revplay.repository.SongRepository;
import com.revplay.service.ArtistService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Scanner;

@Service
@Transactional
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepo;
    private final SongRepository songRepo;

    public ArtistServiceImpl(ArtistRepository artistRepo, SongRepository songRepo) {
        this.artistRepo = artistRepo;
        this.songRepo = songRepo;
    }

    // =========================
    // 🎤 ARTIST MENU (CONSOLE)
    // =========================
    @Override
    public void artistMenu(Scanner sc, User user) {

        // Ensure artist profile exists
        Artist artist = artistRepo.findByUser(user)
                .orElseGet(() -> {
                    Artist newArtist = new Artist();
                    newArtist.setUser(user);

                    System.out.print("Enter artist bio: ");
                    newArtist.setBio(sc.next());

                    System.out.print("Enter artist genre: ");
                    newArtist.setGenre(sc.next());

                    return artistRepo.save(newArtist);
                });

        while (true) {
            System.out.println("""
                🎤 Artist Menu
                -----------------
                1. Upload Song
                2. View My Songs
                0. Back
                """);

            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.print("Song Title: ");
                    String title = sc.next();

                    System.out.print("Genre: ");
                    String genre = sc.next();

                    System.out.print("Duration (seconds): ");
                    int duration = sc.nextInt();

                    uploadSong(artist.getId(), title, genre, duration);
                }

                case 2 -> viewMySongs(artist.getId());

                case 0 -> {
                    System.out.println("⬅️ Returning to User Menu...");
                    return;
                }

                default -> System.out.println("❌ Invalid option");
            }
        }
    }

    // =========================
    // 🎵 UPLOAD SONG
    // =========================
    @Override
    public void uploadSong(Long artistId, String title, String genre, int duration) {
        Artist artist = artistRepo.findById(artistId).orElseThrow();

        Song song = new Song();
        song.setTitle(title);
        song.setGenre(genre);
        song.setDuration(duration);
        song.setPlayCount(0);
        song.setArtist(artist);

        songRepo.save(song);
        System.out.println("🎵 Song uploaded successfully: " + title);
    }

    // =========================
    // 📂 VIEW MY SONGS
    // =========================
    @Override
    public void viewMySongs(Long artistId) {
        songRepo.findAll()
                .stream()
                .filter(s -> s.getArtist() != null)
                .filter(s -> s.getArtist().getId().equals(artistId))
                .forEach(s ->
                        System.out.println("🎶 ID: " + s.getId() + " | " + s.getTitle())
                );
    }

    @Override
    public void viewFavoriteCountForMySongs(Long artistId) {

    }
}
