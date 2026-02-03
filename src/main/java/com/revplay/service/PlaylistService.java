package com.revplay.service;

import com.revplay.entity.Playlist;
import com.revplay.entity.User;

import java.util.List;
import java.util.Scanner;

public interface PlaylistService {

    // CORE BUSINESS METHODS (used by Controller)
    Playlist createPlaylist(Long userId, String name, String description, boolean isPublic);

    void addSongToPlaylist(Long playlistId, Long songId);

    List<Playlist> getUserPlaylists(Long userId);

    // CONSOLE METHODS
    void createPlaylistConsole(Scanner sc, Long userId);

    void addSongConsole(Scanner sc, User loggedInUser);

    void viewUserPlaylists(Long userId);
}
