package com.revplay.controller;

import com.revplay.entity.Playlist;
import com.revplay.service.PlaylistService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @PostMapping("/create")
    public Playlist createPlaylist(
            @RequestParam Long userId,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam boolean isPublic) {

        return playlistService.createPlaylist(userId, name, description, isPublic);
    }

    @GetMapping("/user/{userId}")
    public List<Playlist> getUserPlaylists(@PathVariable Long userId) {
        return playlistService.getUserPlaylists(userId);
    }
}

