package org.AP;

import java.util.ArrayList;
import java.util.List;

public class User extends Account {
    private List<Comment> comments;
    private List<Song> likedSongs;

    public User(String username, String password, String email) {
        super(username, password, email);
        this.comments = new ArrayList<>();
        this.likedSongs = new ArrayList<>();
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void likeSong(Song song) {
        if (!likedSongs.contains(song)) {
            likedSongs.add(song);
            song.incrementLikes();
        }
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<Song> getLikedSongs() {
        return likedSongs;
    }
}