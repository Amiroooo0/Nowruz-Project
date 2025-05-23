package org.AP;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Album {
    private final String id;
    private String title;
    private Artist artist;
    private LocalDate releaseDate;
    private List<Song> songs;
    private String genre;
    private String coverArtUrl;
    private String recordLabel;
    private int totalDuration;
    private final LocalDate createdAt;
    private LocalDate updatedAt;

    public Album(String title, Artist artist, LocalDate releaseDate) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.artist = artist;
        this.releaseDate = releaseDate;
        this.songs = new ArrayList<>();
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
        this.totalDuration = 0;
    }

    // ==================== Song Management ====================
    public void addSong(Song song) {
        if (song == null) {
            throw new IllegalArgumentException("Song cannot be null");
        }

        if (!songs.contains(song)) {
            songs.add(song);
            song.setAlbum(this);
            totalDuration += song.getDurationSeconds();
            updatedAt = LocalDate.now();
        }
    }

    public boolean removeSong(Song song) {
        if (song != null && songs.remove(song)) {
            song.setAlbum(null);
            totalDuration -= song.getDurationSeconds();
            updatedAt = LocalDate.now();
            return true;
        }
        return false;
    }

    public boolean containsSong(Song song) {
        return songs.contains(song);
    }

    // ==================== Duration Calculation ====================
    public String getFormattedDuration() {
        int totalSeconds = songs.stream()
                .mapToInt(Song::getDurationSeconds)
                .sum();
        return String.format("%d:%02d", totalSeconds / 60, totalSeconds % 60);
    }

    public void recalculateDuration() {
        this.totalDuration = songs.stream()
                .mapToInt(Song::getDurationSeconds)
                .sum();
    }

    // ==================== Search Methods ====================
    public List<Song> findSongsByTitle(String titleQuery) {
        return songs.stream()
                .filter(song -> song.getTitle().toLowerCase()
                        .contains(titleQuery.toLowerCase()))
                .collect(Collectors.toList());
    }

    // ==================== Getters & Setters ====================
    public String getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) {
        this.title = title;
        updatedAt = LocalDate.now();
    }

    public Artist getArtist() { return artist; }
    public void setArtist(Artist artist) {
        this.artist = artist;
        updatedAt = LocalDate.now();
    }

    public LocalDate getReleaseDate() { return releaseDate; }
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
        updatedAt = LocalDate.now();
    }

    public List<Song> getSongs() { return Collections.unmodifiableList(songs); }

    public String getGenre() { return genre; }
    public void setGenre(String genre) {
        this.genre = genre;
        updatedAt = LocalDate.now();
    }

    public String getCoverArtUrl() { return coverArtUrl; }
    public void setCoverArtUrl(String coverArtUrl) {
        this.coverArtUrl = coverArtUrl;
        updatedAt = LocalDate.now();
    }

    public String getRecordLabel() { return recordLabel; }
    public void setRecordLabel(String recordLabel) {
        this.recordLabel = recordLabel;
        updatedAt = LocalDate.now();
    }

    public int getTotalDuration() { return totalDuration; }

    public LocalDate getCreatedAt() { return createdAt; }

    public LocalDate getUpdatedAt() { return updatedAt; }

    // ==================== Utility Methods ====================
    public int getSongCount() {
        return songs.size();
    }

    public double getAverageRating() {
        return songs.stream()
                .mapToInt(Song::getLikeCount)
                .average()
                .orElse(0.0);
    }

    @Override
    public String toString() {
        return String.format("%s - %s (%s) [%d songs]",
                artist.getUsername(), title, releaseDate.getYear(), songs.size());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return id.equals(album.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}