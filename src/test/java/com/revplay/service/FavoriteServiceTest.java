package com.revplay.service;

import com.revplay.entity.Song;
import com.revplay.entity.User;
import com.revplay.repository.SongRepository;
import com.revplay.repository.UserRepository;
import com.revplay.service.impl.FavoriteServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.mockito.Mockito.*;

class FavoriteServiceTest {

    @Mock
    private UserRepository userRepo;

    @Mock
    private SongRepository songRepo;

    @InjectMocks
    private FavoriteServiceImpl favoriteService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addToFavorites_success() {
        User user = new User();
        user.setId(1L);

        Song song = new Song();
        song.setId(2L);
        song.setTitle("Song A");

        when(userRepo.findById(1L))
                .thenReturn(Optional.of(user));
        when(songRepo.findById(2L))
                .thenReturn(Optional.of(song));

        favoriteService.addToFavorites(1L, 2L);

        verify(userRepo).save(user);
    }
}
