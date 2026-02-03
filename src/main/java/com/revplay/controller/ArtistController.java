package com.revplay.controller;

import com.revplay.service.ArtistService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    // 🎤 Upload song
    @PostMapping("/{artistId}/songs")
    public String uploadSong(
            @PathVariable Long artistId,
            @RequestParam String title,
            @RequestParam String genre,
            @RequestParam int duration) {

        artistService.uploadSong(artistId, title, genre, duration);
        return "Song uploaded successfully";
    }

    // 📊 Artist stats
    @GetMapping("/{artistId}/stats")
    public void stats(@PathVariable Long artistId) {
        artistService.viewMySongs(artistId);
    }
}
