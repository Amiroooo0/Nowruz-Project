package org.AP;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AuthService {
    private final List<Account> accounts;
    private final List<Artist> pendingArtists;
    private final Admin mainAdmin;

    public AuthService() {
        this.accounts = new ArrayList<>();
        this.pendingArtists = new ArrayList<>();
        this.mainAdmin = new Admin("amir", "amir123456", "admin@genius.com");
        accounts.add(mainAdmin);
    }

    // متدهای موجود
    public boolean registerUser(User user) {
        if (usernameExists(user.getUsername())) {
            return false;
        }
        accounts.add(user);
        return true;
    }

    public boolean registerArtist(Artist artist) {
        if (usernameExists(artist.getUsername())) {
            return false;
        }
        pendingArtists.add(artist);
        return true;
    }

    public boolean approveArtist(String artistId) {
        Optional<Artist> artist = pendingArtists.stream()
                .filter(a -> a.getId().equals(artistId))
                .findFirst();

        if (artist.isPresent()) {
            Artist approvedArtist = artist.get();
            pendingArtists.remove(approvedArtist);
            approvedArtist.setApproved(true);
            accounts.add(approvedArtist);
            return true;
        }
        return false;
    }

    public List<Artist> getPendingArtists() {
        return new ArrayList<>(pendingArtists);
    }

    public Account login(String username, String password) {
        return accounts.stream()
                .filter(acc -> acc.getUsername().equals(username) && acc.authenticate(password))
                .findFirst()
                .orElse(null);
    }

    // متدهای جدید
    public List<Account> getAllAccounts() {
        return new ArrayList<>(accounts);
    }

    public Artist findArtistByUsername(String username) {
        return accounts.stream()
                .filter(acc -> acc instanceof Artist && acc.getUsername().equalsIgnoreCase(username))
                .map(acc -> (Artist) acc)
                .findFirst()
                .orElse(null);
    }

    public List<Artist> searchArtists(String query) {
        String lowerQuery = query.toLowerCase();
        return accounts.stream()
                .filter(acc -> acc instanceof Artist)
                .map(acc -> (Artist) acc)
                .filter(artist -> artist.getUsername().toLowerCase().contains(lowerQuery))
                .collect(Collectors.toList());
    }

    private boolean usernameExists(String username) {
        return accounts.stream().anyMatch(acc -> acc.getUsername().equals(username)) ||
                pendingArtists.stream().anyMatch(artist -> artist.getUsername().equals(username));
    }
}