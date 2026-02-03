package com.revplay.service;

import com.revplay.entity.User;

import java.util.Scanner;

public interface ArtistService {
    void uploadSong(Long artistId, String title, String genre, int duration);
    void viewMySongs(Long artistId);

    void viewFavoriteCountForMySongs(Long artistId);

    void artistMenu(Scanner sc, User user);
}
