package org.AP;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Song {
    private final String id;
    private String title;
    private String lyrics;
    private Artist artist;
    private Album album;
    private int viewCount;
    private int likeCount;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<String> tags;
    private String genre;
    private int durationSeconds;
    private boolean isExplicit;
    private String language;
    private String copyrightInfo;
    private List<String> writers;
    private List<String> producers;
    private LocalDate releaseDate;


    public Song(String title, String lyrics, Artist artist) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.lyrics = lyrics;
        this.artist = artist;
        this.viewCount = 0;
        this.likeCount = 0;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.tags = new ArrayList<>();
        this.writers = new ArrayList<>();
        this.producers = new ArrayList<>();
        this.isExplicit = false;
        this.language = "English";
    }
    // اضافه کردن این فیلدها به کلاس Song



    // اضافه کردن متدهای زیر
    public LocalDate getReleaseDate() {
        return releaseDate;
    }


    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
        this.updatedAt = LocalDateTime.now();
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
        this.updatedAt = LocalDateTime.now();
    }

    // ==================== Core Methods ====================
    public void incrementViews() {
        this.viewCount++;
        this.updatedAt = LocalDateTime.now();
    }

    public void incrementLikes() {
        this.likeCount++;
        this.updatedAt = LocalDateTime.now();
    }

    public void addTag(String tag) {
        if (!tags.contains(tag.toLowerCase())) {
            tags.add(tag.toLowerCase());
            this.updatedAt = LocalDateTime.now();
        }
    }

    public void addWriter(String writer) {
        if (!writers.contains(writer)) {
            writers.add(writer);
            this.updatedAt = LocalDateTime.now();
        }
    }

    public void addProducer(String producer) {
        if (!producers.contains(producer)) {
            producers.add(producer);
            this.updatedAt = LocalDateTime.now();
        }
    }

    // ==================== Getters & Setters ====================
    public String getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) {
        this.title = title;
        this.updatedAt = LocalDateTime.now();
    }

    public String getLyrics() { return lyrics; }
    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
        this.updatedAt = LocalDateTime.now();
    }

    public Artist getArtist() { return artist; }

    public Album getAlbum() { return album; }
    public void setAlbum(Album album) {
        this.album = album;
        this.updatedAt = LocalDateTime.now();
    }

    public int getViewCount() { return viewCount; }

    public int getLikeCount() { return likeCount; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public List<String> getTags() { return Collections.unmodifiableList(tags); }




    public int getDurationSeconds() { return durationSeconds; }
    public void setDurationSeconds(int durationSeconds) {
        this.durationSeconds = durationSeconds;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isExplicit() { return isExplicit; }
    public void setExplicit(boolean explicit) {
        isExplicit = explicit;
        this.updatedAt = LocalDateTime.now();
    }

    public String getLanguage() { return language; }
    public void setLanguage(String language) {
        this.language = language;
        this.updatedAt = LocalDateTime.now();
    }

    public String getCopyrightInfo() { return copyrightInfo; }
    public void setCopyrightInfo(String copyrightInfo) {
        this.copyrightInfo = copyrightInfo;
        this.updatedAt = LocalDateTime.now();
    }

    public List<String> getWriters() { return Collections.unmodifiableList(writers); }

    public List<String> getProducers() { return Collections.unmodifiableList(producers); }

    // ==================== Utility Methods ====================
    public String getFormattedDuration() {
        return String.format("%d:%02d", durationSeconds / 60, durationSeconds % 60);
    }

    public boolean hasTag(String tag) {
        return tags.contains(tag.toLowerCase());
    }

    public double getPopularityScore() {
        return (viewCount * 0.6) + (likeCount * 0.4);
    }

    @Override
    public String toString() {
        return String.format("%s - %s (%s) [%d views, %d likes]",
                artist.getUsername(), title, getFormattedDuration(), viewCount, likeCount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return id.equals(song.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}