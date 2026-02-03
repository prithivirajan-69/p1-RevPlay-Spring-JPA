package com.revplay.service;

import com.revplay.entity.Playlist;
import com.revplay.entity.User;
import com.revplay.repository.PlaylistRepository;
import com.revplay.repository.UserRepository;
import com.revplay.service.impl.PlaylistServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlaylistServiceTest {

    @Mock
    private PlaylistRepository playlistRepo;

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private PlaylistServiceImpl playlistService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPlaylist_success() {

        User user = new User();
        user.setId(1L);

        Playlist playlist = new Playlist();
        playlist.setId(10L);
        playlist.setName("My Playlist");

        // MOCKS
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(playlistRepo.save(any(Playlist.class))).thenReturn(playlist);

        Playlist result = playlistService.createPlaylist(
                1L,
                "My Playlist",
                "Test Desc",
                true
        );

        assertNotNull(result);
        assertEquals("My Playlist", result.getName());
    }

}
