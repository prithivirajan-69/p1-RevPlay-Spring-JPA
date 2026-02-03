package com.revplay.service;

import com.revplay.entity.Artist;

import java.util.Scanner;

public interface AlbumService {

    void createAlbum(Scanner sc, Artist artist);

    void addSongToAlbum(Scanner sc, Artist artist);

    void viewMyAlbums(Long artistId);

    void deleteAlbum(Scanner sc, Artist artist);
}
