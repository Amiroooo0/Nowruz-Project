package org.AP;

import java.util.ArrayList;
import java.util.List;

public class Artist extends Account {
    private boolean approved;
    private List<Song> songs;
    private List<Album> albums;
    private String bio;
    private String profileImageUrl;
    private List<String> socialMediaLinks;

    public Artist(String username, String password, String email) {
        super(username, password, email);
        this.approved = false;
        this.songs = new ArrayList<>();
        this.albums = new ArrayList<>();
        this.bio = "";
        this.profileImageUrl = "";
        this.socialMediaLinks = new ArrayList<>();
    }

    // متدهای اصلی
    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public void addAlbum(Album album) {
        albums.add(album);
    }

    public void editSongLyrics(Song song, String newLyrics) {
        if (songs.contains(song)) {
            song.setLyrics(newLyrics);
        }
    }

    // Getters and Setters
    public List<Song> getSongs() {
        return songs;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public List<String> getSocialMediaLinks() {
        return socialMediaLinks;
    }

    public void addSocialMediaLink(String link) {
        socialMediaLinks.add(link);
    }

    public String viewProfile() {
        StringBuilder profile = new StringBuilder();
        profile.append("=== Artist Profile ===\n");
        profile.append("Username: ").append(getUsername()).append("\n");
        profile.append("Email: ").append(getEmail()).append("\n");
        profile.append("Bio: ").append(bio).append("\n");
        profile.append("Songs: ").append(songs.size()).append("\n");
        profile.append("Albums: ").append(albums.size()).append("\n");
        profile.append("Social Media Links: ").append(socialMediaLinks.size()).append("\n");
        return profile.toString();
    }
}