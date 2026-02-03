package com.revplay.controller;

import com.revplay.entity.Song;
import com.revplay.service.MusicPlayerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/player")
public class MusicPlayerController {

    private final MusicPlayerService playerService;

    public MusicPlayerController(MusicPlayerService playerService) {
        this.playerService = playerService;
    }

    // ▶️ Play song
    @PostMapping("/play")
    public Song play(
            @RequestParam Long userId,
            @RequestParam Long songId) {

        return playerService.playSong(userId, songId);
    }
}
