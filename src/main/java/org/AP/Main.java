package org.AP;

import java.time.LocalDate;

import java.util.List;
import java.util.Scanner;


public class Main {
    private static AuthService authService;
    private static MusicService musicService;
    private static Scanner scanner;

    public static void main(String[] args) {
        authService = new AuthService();
        musicService = new MusicService();
        scanner = new Scanner(System.in);

        System.out.println("=== Genius Music Platform ===");
        initializeTestData();
        showMainMenu();
    }

    private static void initializeTestData() {
        Artist artist = new Artist("queen", "bohemian", "queen@example.com");
        authService.registerArtist(artist);
        authService.approveArtist(artist.getId());

        Song song = new Song("Bohemian Rhapsody", "Mama...", artist);
        musicService.addSong(song);
    }

    private static void showMainMenu() {
        while (true) {
            System.out.println("\n=== Main Menu ===");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> handleLogin();
                case 2 -> handleRegistration();
                case 3 -> System.exit(0);
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private static void handleLogin() {
        System.out.print("\nUsername: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        Account account = authService.login(username, password);
        if (account != null) {
            if (account instanceof Admin) {
                handleAdminMenu((Admin) account);
            } else if (account instanceof Artist) {
                handleArtistMenu((Artist) account);
            } else {
                handleUserMenu((User) account);
            }
        } else {
            System.out.println("Invalid credentials or artist not approved!");
        }
    }

    private static void handleRegistration() {
        System.out.println("\n=== Registration ===");
        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.println("Account Type:");
        System.out.println("1. Regular User");
        System.out.println("2. Artist");
        System.out.print("Choose: ");
        int type = scanner.nextInt();
        scanner.nextLine();

        if (type == 1) {
            User user = new User(username, password, email);
            if (authService.registerUser(user)) {
                System.out.println("User registered successfully!");
            } else {
                System.out.println("Username already exists!");
            }
        } else if (type == 2) {
            Artist artist = new Artist(username, password, email);
            if (authService.registerArtist(artist)) {
                System.out.println("Artist request submitted! Waiting for admin approval.");
            } else {
                System.out.println("Username already exists!");
            }
        }
    }

    // Admin Menu Methods
    private static void handleAdminMenu(Admin admin) {
        while (true) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. View Pending Artists");
            System.out.println("2. Approve Artist");
            System.out.println("3. View All Users");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> viewPendingArtists();
                case 2 -> approveArtist();
                case 3 -> viewAllUsers();
                case 4 -> { return; }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private static void viewPendingArtists() {
        List<Artist> pendingArtists = authService.getPendingArtists();
        if (pendingArtists.isEmpty()) {
            System.out.println("No pending artists!");
        } else {
            System.out.println("\n=== Pending Artists ===");
            pendingArtists.forEach(artist ->
                    System.out.println(artist.getId() + " - " + artist.getUsername()));
        }
    }

    private static void approveArtist() {
        System.out.print("Enter artist ID to approve: ");
        String artistId = scanner.nextLine();
        if (authService.approveArtist(artistId)) {
            System.out.println("Artist approved successfully!");
        } else {
            System.out.println("Artist not found!");
        }
    }

    private static void viewAllUsers() {
        List<Account> users = authService.getAllAccounts();
        if (users.isEmpty()) {
            System.out.println("No users found!");
        } else {
            System.out.println("\n=== All Users ===");
            users.forEach(user -> {
                String type = user instanceof Admin ? "Admin" :
                        user instanceof Artist ? "Artist" : "User";
                System.out.println(user.getUsername() + " (" + type + ")");
            });
        }
    }

    // Artist Menu Methods
    private static void handleArtistMenu(Artist artist) {
        while (true) {
            System.out.println("\n=== Artist Menu ===");
            System.out.println("1. Add New Song");
            System.out.println("2. Create New Album");
            System.out.println("3. View My Songs");
            System.out.println("4. View My Albums");
            System.out.println("5. Edit Song Lyrics");
            System.out.println("6. View My Profile");
            System.out.println("7. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addSong(artist);
                case 2 -> createAlbum(artist);
                case 3 -> viewArtistSongs(artist);
                case 4 -> viewArtistAlbums(artist);
                case 5 -> editSongLyrics(artist);
                case 6 -> viewArtistProfile(artist);
                case 7 -> { return; }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private static void addSong(Artist artist) {
        try {
            System.out.print("Enter song title: ");
            String title = scanner.nextLine();

            System.out.print("Enter genre: ");
            String genre = scanner.nextLine();

            System.out.print("Enter release date (YYYY-MM-DD): ");
            LocalDate releaseDate = LocalDate.parse(scanner.nextLine());

            System.out.print("Enter duration in seconds: ");
            int duration = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Enter lyrics (type END on new line to finish):");
            StringBuilder lyrics = new StringBuilder();
            String line;
            while (!(line = scanner.nextLine()).equals("END")) {
                lyrics.append(line).append("\n");
            }

            Song song = new Song(title, lyrics.toString(), artist);
            song.setGenre(genre);
            song.setDurationSeconds(duration);
            song.setReleaseDate(releaseDate);

            musicService.addSong(song);
            artist.addSong(song);
            System.out.println("Song added successfully!");
        } catch (Exception e) {
            System.out.println("Error adding song: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private static void createAlbum(Artist artist) {
        System.out.print("Enter album title: ");
        String title = scanner.nextLine();

        System.out.print("Enter release date (YYYY-MM-DD): ");
        LocalDate releaseDate = LocalDate.parse(scanner.nextLine());

        Album album = new Album(title, artist, releaseDate);

        while (true) {
            System.out.println("\nCurrent songs in album: " + album.getSongs().size());
            System.out.println("1. Add song to album");
            System.out.println("2. Finish album creation");
            System.out.print("Choose: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                viewArtistSongs(artist);
                System.out.print("Enter song number to add: ");
                int songIndex = scanner.nextInt() - 1;
                scanner.nextLine();

                if (songIndex >= 0 && songIndex < artist.getSongs().size()) {
                    Song song = artist.getSongs().get(songIndex);
                    album.addSong(song);
                    System.out.println("Song added to album!");
                } else {
                    System.out.println("Invalid song number!");
                }
            } else if (choice == 2) {
                break;
            }
        }

        musicService.addAlbum(album);
        artist.addAlbum(album);
        System.out.println("Album created successfully!");
    }

    private static void viewArtistSongs(Artist artist) {
        List<Song> songs = musicService.getSongsByArtist(artist.getId());
        if (songs.isEmpty()) {
            System.out.println("No songs yet!");
        } else {
            System.out.println("\n=== Your Songs ===");
            for (int i = 0; i < songs.size(); i++) {
                Song song = songs.get(i);
                System.out.printf("%d. %s (%s) - %d views, %d likes\n",
                        i + 1,
                        song.getTitle(),
                        song.getGenre(),
                        song.getViewCount(),
                        song.getLikeCount());
            }
        }
    }

    private static void viewArtistAlbums(Artist artist) {
        List<Album> albums = musicService.getAlbumsByArtist(artist.getId());
        if (albums.isEmpty()) {
            System.out.println("No albums yet!");
        } else {
            System.out.println("\n=== Your Albums ===");
            for (int i = 0; i < albums.size(); i++) {
                System.out.println((i + 1) + ". " + albums.get(i).getTitle() +
                        " (" + albums.get(i).getSongs().size() + " songs)");
            }
        }
    }

    private static void editSongLyrics(Artist artist) {
        viewArtistSongs(artist);
        System.out.print("Enter song number to edit: ");
        int songIndex = scanner.nextInt() - 1;
        scanner.nextLine();

        if (songIndex >= 0 && songIndex < artist.getSongs().size()) {
            Song song = artist.getSongs().get(songIndex);
            System.out.println("Current lyrics:\n" + song.getLyrics());
            System.out.println("Enter new lyrics (type END on new line to finish):");

            StringBuilder lyrics = new StringBuilder();
            String line;
            while (!(line = scanner.nextLine()).equals("END")) {
                lyrics.append(line).append("\n");
            }

            song.setLyrics(lyrics.toString());
            System.out.println("Lyrics updated successfully!");
        } else {
            System.out.println("Invalid song number!");
        }
    }

    private static void viewArtistProfile(Artist artist) {
        System.out.println("\n=== Artist Profile ===");
        System.out.println("Username: " + artist.getUsername());
        System.out.println("Email: " + artist.getEmail());
        System.out.println("Total Songs: " + artist.getSongs().size());
        System.out.println("Total Albums: " + artist.getAlbums().size());

        System.out.println("\n=== Top Songs ===");
        artist.getSongs().stream()
                .sorted((s1, s2) -> Integer.compare(s2.getViewCount(), s1.getViewCount()))
                .limit(3)
                .forEach(song -> System.out.println("- " + song.getTitle() +
                        " (" + song.getViewCount() + " views)"));

        System.out.println("\n=== Albums ===");
        artist.getAlbums().forEach(album ->
                System.out.println("- " + album.getTitle() +
                        " (" + album.getSongs().size() + " songs)"));
    }

    // User Menu Methods
    private static void handleUserMenu(User user) {
        while (true) {
            System.out.println("\n=== User Menu ===");
            System.out.println("1. Browse All Songs");
            System.out.println("2. Search Songs");
            System.out.println("3. Search Artists");
            System.out.println("4. View Artist Profile");
            System.out.println("5. Add Comment to Song");
            System.out.println("6. View Song Comments");
            System.out.println("7. Like a Song");
            System.out.println("8. View My Liked Songs");
            System.out.println("9. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> browseAllSongs(user);
                case 2 -> searchSongs(user);
                case 3 -> searchArtists();
                case 4 -> viewArtistProfileByUser();
                case 5 -> addCommentToSong(user);
                case 6 -> viewSongComments();
                case 7 -> likeSong(user);
                case 8 -> viewLikedSongs(user);
                case 9 -> { return; }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private static void browseAllSongs(User user) {
        List<Song> songs = musicService.getAllSongs();
        if (songs.isEmpty()) {
            System.out.println("No songs available!");
        } else {
            System.out.println("\n=== All Songs ===");
            for (int i = 0; i < songs.size(); i++) {
                Song song = songs.get(i);
                System.out.printf("%d. %s - %s (%d views, %d likes)\n",
                        i + 1,
                        song.getTitle(),
                        song.getArtist().getUsername(),
                        song.getViewCount(),
                        song.getLikeCount());
            }

            System.out.print("\nEnter song number to view details (0 to go back): ");
            int selection = scanner.nextInt();
            scanner.nextLine();

            if (selection > 0 && selection <= songs.size()) {
                viewSongDetails(songs.get(selection - 1), user);
            }
        }
    }

    private static void viewSongDetails(Song song, User user) {
        song.incrementViews();
        System.out.println("\n=== " + song.getTitle() + " ===");
        System.out.println("Artist: " + song.getArtist().getUsername());
        System.out.println("Genre: " + song.getGenre());
        System.out.println("Duration: " + song.getFormattedDuration());
        System.out.println("Views: " + song.getViewCount());
        System.out.println("Likes: " + song.getLikeCount());
        System.out.println("\nLyrics:\n" + song.getLyrics());

        System.out.println("\n=== Comments ===");
        List<Comment> comments = musicService.getCommentsForSong(song.getId());
        if (comments.isEmpty()) {
            System.out.println("No comments yet!");
        } else {
            comments.forEach(comment -> System.out.println(
                    comment.getAuthor().getUsername() + ": " +
                            comment.getText() + " (" + comment.getCreatedAt() + ")"));
        }
    }

    private static void searchSongs(User user) {
        System.out.print("Enter song title to search: ");
        String query = scanner.nextLine();
        List<Song> results = musicService.searchSongs(query);

        if (results.isEmpty()) {
            System.out.println("No songs found!");
        } else {
            System.out.println("\n=== Search Results ===");
            for (int i = 0; i < results.size(); i++) {
                Song song = results.get(i);
                System.out.printf("%d. %s - %s (%d views)\n",
                        i + 1,
                        song.getTitle(),
                        song.getArtist().getUsername(),
                        song.getViewCount());
            }

            System.out.print("\nSelect song to view details (0 to go back): ");
            int selection = scanner.nextInt();
            scanner.nextLine();

            if (selection > 0 && selection <= results.size()) {
                viewSongDetails(results.get(selection - 1), user);
            }
        }
    }

    private static void searchArtists() {
        System.out.print("Enter artist name to search: ");
        String query = scanner.nextLine();
        List<Artist> results = authService.searchArtists(query);

        if (results.isEmpty()) {
            System.out.println("No artists found!");
        } else {
            System.out.println("\n=== Search Results ===");
            for (int i = 0; i < results.size(); i++) {
                Artist artist = results.get(i);
                System.out.printf("%d. %s (%d songs)\n",
                        i + 1,
                        artist.getUsername(),
                        musicService.getSongsByArtist(artist.getId()).size());
            }

            System.out.print("\nSelect artist to view profile (0 to go back): ");
            int selection = scanner.nextInt();
            scanner.nextLine();

            if (selection > 0 && selection <= results.size()) {
                viewArtistProfile(results.get(selection - 1));
            }
        }
    }

    private static void viewArtistProfileByUser() {
        System.out.print("Enter artist username: ");
        String username = scanner.nextLine();
        Artist artist = authService.findArtistByUsername(username);

        if (artist == null) {
            System.out.println("Artist not found!");
        } else {
            viewArtistProfile(artist);
        }
    }

    private static void addCommentToSong(User user) {
        System.out.print("Enter song title to comment on: ");
        String title = scanner.nextLine();
        List<Song> results = musicService.searchSongs(title);

        if (results.isEmpty()) {
            System.out.println("No songs found!");
        } else if (results.size() == 1) {
            addCommentToSong(user, results.get(0));
        } else {
            System.out.println("\nMultiple songs found:");
            for (int i = 0; i < results.size(); i++) {
                System.out.printf("%d. %s - %s\n",
                        i + 1,
                        results.get(i).getTitle(),
                        results.get(i).getArtist().getUsername());
            }

            System.out.print("Select song to comment on: ");
            int selection = scanner.nextInt();
            scanner.nextLine();

            if (selection > 0 && selection <= results.size()) {
                addCommentToSong(user, results.get(selection - 1));
            }
        }
    }

    private static void addCommentToSong(User user, Song song) {
        System.out.print("Enter your comment: ");
        String text = scanner.nextLine();

        Comment comment = new Comment(user, song, text);
        musicService.addComment(comment);
        user.addComment(comment);
        System.out.println("Comment added successfully!");
    }

    private static void viewSongComments() {
        System.out.print("Enter song title: ");
        String title = scanner.nextLine();
        List<Song> results = musicService.searchSongs(title);

        if (results.isEmpty()) {
            System.out.println("No songs found!");
        } else if (results.size() == 1) {
            showSongComments(results.get(0));
        } else {
            System.out.println("\nMultiple songs found:");
            for (int i = 0; i < results.size(); i++) {
                System.out.printf("%d. %s - %s\n",
                        i + 1,
                        results.get(i).getTitle(),
                        results.get(i).getArtist().getUsername());
            }

            System.out.print("Select song to view comments: ");
            int selection = scanner.nextInt();
            scanner.nextLine();

            if (selection > 0 && selection <= results.size()) {
                showSongComments(results.get(selection - 1));
            }
        }
    }

    private static void showSongComments(Song song) {
        List<Comment> comments = musicService.getCommentsForSong(song.getId());
        System.out.println("\n=== Comments for " + song.getTitle() + " ===");

        if (comments.isEmpty()) {
            System.out.println("No comments yet!");
        } else {
            comments.forEach(comment -> System.out.println(
                    comment.getAuthor().getUsername() + ": " +
                            comment.getText() + "\n(" + comment.getCreatedAt() + ")\n"));
        }
    }

    private static void likeSong(User user) {
        System.out.print("Enter song title to like: ");
        String title = scanner.nextLine();
        List<Song> results = musicService.searchSongs(title);

        if (results.isEmpty()) {
            System.out.println("No songs found!");
        } else if (results.size() == 1) {
            likeSong(user, results.get(0));
        } else {
            System.out.println("\nMultiple songs found:");
            for (int i = 0; i < results.size(); i++) {
                System.out.printf("%d. %s - %s\n",
                        i + 1,
                        results.get(i).getTitle(),
                        results.get(i).getArtist().getUsername());
            }

            System.out.print("Select song to like: ");
            int selection = scanner.nextInt();
            scanner.nextLine();

            if (selection > 0 && selection <= results.size()) {
                likeSong(user, results.get(selection - 1));
            }
        }
    }

    private static void likeSong(User user, Song song) {
        if (user.getLikedSongs().contains(song)) {
            System.out.println("You already liked this song!");
        } else {
            user.likeSong(song);
            song.incrementLikes();
            System.out.println("Song liked successfully!");
        }
    }

    private static void viewLikedSongs(User user) {
        List<Song> likedSongs = user.getLikedSongs();
        if (likedSongs.isEmpty()) {
            System.out.println("You haven't liked any songs yet!");
        } else {
            System.out.println("\n=== Your Liked Songs ===");
            likedSongs.forEach(song ->
                    System.out.println("- " + song.getTitle() +
                            " by " + song.getArtist().getUsername()));
        }
    }
}