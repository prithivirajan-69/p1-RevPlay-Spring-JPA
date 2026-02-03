package com.revplay.service;

import com.revplay.entity.Song;
import com.revplay.entity.User;

import java.util.Scanner;

public interface MusicPlayerService {

    void playSong(Scanner sc, User user);

    Song playSong(Long userId, Long songId);

    void pauseSong();

    void skipSong();

    void repeatSong();
}
