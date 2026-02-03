package com.revplay.service.impl;

import com.revplay.entity.ListeningHistory;
import com.revplay.entity.Song;
import com.revplay.entity.User;
import com.revplay.repository.ListeningHistoryRepository;
import com.revplay.repository.SongRepository;
import com.revplay.repository.UserRepository;
import com.revplay.service.MusicPlayerService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Scanner;

@Service
@Transactional
public class MusicPlayerServiceImpl implements MusicPlayerService {

    private final SongRepository songRepo;
    private final ListeningHistoryRepository historyRepo;
    private final UserRepository userRepo;

    // 🎵 Keeps track of last played song
    private Song currentSong;

    public MusicPlayerServiceImpl(
            SongRepository songRepo,
            ListeningHistoryRepository historyRepo,
            UserRepository userRepo) {

        this.songRepo = songRepo;
        this.historyRepo = historyRepo;
        this.userRepo = userRepo;
    }

    // ===============================
    // 🎧 CONSOLE PLAY
    // ===============================
    @Override
    public void playSong(Scanner sc, User user) {

        System.out.print("Enter Song ID: ");
        Long songId = sc.nextLong();

        playSong(user.getId(), songId);
    }

    // ===============================
    // 🎧 CORE PLAY LOGIC
    // ===============================
    @Override
    public Song playSong(Long userId, Long songId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Song song = songRepo.findById(songId)
                .orElseThrow(() -> new RuntimeException("Song not found"));

        song.setPlayCount(song.getPlayCount() + 1);
        songRepo.save(song);

        ListeningHistory history = new ListeningHistory();
        history.setUser(user);
        history.setSong(song);
        history.setPlayedAt(LocalDateTime.now());

        historyRepo.save(history);

        currentSong = song;

        System.out.println("▶️ Playing: " + song.getTitle());
        return song;
    }

    // ===============================
    // ⏸ PAUSE
    // ===============================
    @Override
    public void pauseSong() {
        if (currentSong == null) {
            System.out.println("⚠ No song is currently playing");
            return;
        }
        System.out.println("⏸ Paused: " + currentSong.getTitle());
    }

    // ===============================
    // ⏭ SKIP
    // ===============================
    @Override
    public void skipSong() {
        if (currentSong == null) {
            System.out.println("⚠ No song to skip");
            return;
        }
        System.out.println("⏭ Skipped: " + currentSong.getTitle());
        currentSong = null;
    }

    // ===============================
    // 🔁 REPEAT
    // ===============================
    @Override
    public void repeatSong() {
        if (currentSong == null) {
            System.out.println("⚠ No song to repeat");
            return;
        }
        System.out.println("🔁 Replaying: " + currentSong.getTitle());
    }
}
