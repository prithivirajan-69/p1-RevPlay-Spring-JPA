package com.revplay.console;

import com.revplay.entity.User;
import com.revplay.service.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleAppRunner implements CommandLineRunner {

    private final UserService userService;
    private final PlaylistService playlistService;
    private final FavoriteService favoriteService;
    private final MusicPlayerService musicPlayerService;
    private final ArtistService artistService;

    public ConsoleAppRunner(
            UserService userService,
            PlaylistService playlistService,
            FavoriteService favoriteService,
            MusicPlayerService musicPlayerService,
            ArtistService artistService) {

        this.userService = userService;
        this.playlistService = playlistService;
        this.favoriteService = favoriteService;
        this.musicPlayerService = musicPlayerService;
        this.artistService = artistService;
    }

    @Override
    public void run(String... args) {

        Scanner sc = new Scanner(System.in);
        User loggedInUser = null;

        System.out.println("🎵 REVPLAY CONSOLE STARTED");

        while (true) {

            // =========================
            // NOT LOGGED IN
            // =========================
            if (loggedInUser == null) {
                showMainMenu();

                int choice = sc.nextInt();
                sc.nextLine(); // consume newline

                switch (choice) {

                    case 1 -> {
                        System.out.print("Name: ");
                        String name = sc.nextLine();

                        System.out.print("Email: ");
                        String email = sc.nextLine();

                        System.out.print("Password: ");
                        String password = sc.nextLine();

                        userService.register(name, email, password);
                        System.out.println("✅ Registration successful");
                    }

                    case 2 -> {
                        System.out.print("Email: ");
                        String email = sc.nextLine();

                        System.out.print("Password: ");
                        String password = sc.nextLine();

                        loggedInUser = userService.login(email, password);
                        System.out.println("✅ Welcome " + loggedInUser.getName());
                    }

                    case 0 -> {
                        System.out.println("👋 Exiting RevPlay...");
                        return;
                    }

                    default -> System.out.println("❌ Invalid choice");
                }

            }

            // =========================
            // LOGGED IN USER MENU
            // =========================
            else {
                showUserMenu();

                int choice = sc.nextInt();
                sc.nextLine(); // consume newline

                switch (choice) {

                    case 1 -> playlistService.createPlaylistConsole(sc, loggedInUser.getId());

                    case 2 -> playlistService.addSongConsole(sc, loggedInUser);

                    case 3 -> playlistService.viewUserPlaylists(loggedInUser.getId());

                    case 4 -> favoriteService.addFavorite(sc, loggedInUser);

                    case 5 -> favoriteService.viewFavorites(loggedInUser.getId());

                    case 6 -> musicPlayerService.playSong(sc, loggedInUser);

                    case 7 -> artistService.artistMenu(sc, loggedInUser);

                    case 9 -> {
                        loggedInUser = null;
                        System.out.println("🔓 Logged out");
                    }

                    default -> System.out.println("❌ Invalid option");
                }
            }
        }
    }

    private void showMainMenu() {
        System.out.println("""
                
                🎵 REVPLAY
                1. Register User
                2. Login
                0. Exit
                """);
    }

    private void showUserMenu() {
        System.out.println("""
                
                🎧 USER MENU
                1. Create Playlist
                2. Add Song to Playlist
                3. View My Playlists
                4. Add Favorite Song
                5. View Favorite Songs
                6. Play Song
                7. Artist Menu
                9. Logout
                """);
    }
}
