package com.revplay.service.impl;

import com.revplay.entity.Album;
import com.revplay.entity.Artist;
import com.revplay.entity.Song;
import com.revplay.repository.AlbumRepository;
import com.revplay.repository.SongRepository;
import com.revplay.service.AlbumService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Scanner;

@Service
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepo;
    private final SongRepository songRepo;

    public AlbumServiceImpl(AlbumRepository albumRepo, SongRepository songRepo) {
        this.albumRepo = albumRepo;
        this.songRepo = songRepo;
    }

    @Override
    public void createAlbum(Scanner sc, Artist artist) {
        System.out.print("Album title: ");
        String title = sc.next();

        Album album = new Album();
        album.setTitle(title);
        album.setArtist(artist);
        album.setReleaseDate(LocalDate.now());

        albumRepo.save(album);
        System.out.println("🎧 Album created successfully");
    }

    @Override
    public void addSongToAlbum(Scanner sc, Artist artist) {
        System.out.print("Album ID: ");
        Long albumId = sc.nextLong();

        System.out.print("Song ID: ");
        Long songId = sc.nextLong();

        Album album = albumRepo.findById(albumId).orElseThrow();
        Song song = songRepo.findById(songId).orElseThrow();

        album.getSongs().add(song);
        albumRepo.save(album);

        System.out.println("🎵 Song added to album");
    }

    @Override
    public void viewMyAlbums(Long artistId) {
        albumRepo.findByArtistId(artistId).forEach(album -> {
            System.out.println("📀 " + album.getId() + " - " + album.getTitle());
            album.getSongs().forEach(song ->
                    System.out.println("   🎵 " + song.getTitle()));
        });
    }

    @Override
    public void deleteAlbum(Scanner sc, Artist artist) {
        System.out.print("Album ID to delete: ");
        Long id = sc.nextLong();
        albumRepo.deleteById(id);
        System.out.println("❌ Album deleted");
    }
}
