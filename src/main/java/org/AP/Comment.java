package org.AP;

import java.time.LocalDateTime;
import java.util.UUID;

public class Comment {
    private String id;
    private User author;
    private Song song;
    private String text;
    private LocalDateTime createdAt;

    public Comment(User author, Song song, String text) {
        this.id = UUID.randomUUID().toString();
        this.author = author;
        this.song = song;
        this.text = text;
        this.createdAt = LocalDateTime.now();
    }

    // Getters
    public String getId() { return id; }
    public User getAuthor() { return author; }
    public Song getSong() { return song; }
    public String getText() { return text; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        return String.format("%s - %s:\n%s\n(%s)",
                author.getUsername(),
                song.getTitle(),
                text,
                createdAt.toString());
    }
}