package org.AP;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MusicService {
    private List<Song> songs = new ArrayList<>();
    private List<Album> albums = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();

    // متدهای موجود برای مدیریت آهنگ‌ها
    public void addSong(Song song) {
        songs.add(song);
    }

    public List<Song> getAllSongs() {
        return new ArrayList<>(songs);
    }

    public List<Song> getSongsByArtist(String artistId) {
        return songs.stream()
                .filter(song -> song.getArtist().getId().equals(artistId))
                .collect(Collectors.toList());
    }

    public List<Song> searchSongs(String query) {
        String lowerQuery = query.toLowerCase();
        return songs.stream()
                .filter(song -> song.getTitle().toLowerCase().contains(lowerQuery) ||
                        song.getArtist().getUsername().toLowerCase().contains(lowerQuery))
                .collect(Collectors.toList());
    }

    // متدهای جدید برای مدیریت آلبوم‌ها
    public void addAlbum(Album album) {
        albums.add(album);
    }

    public List<Album> getAlbumsByArtist(String artistId) {
        return albums.stream()
                .filter(album -> album.getArtist().getId().equals(artistId))
                .collect(Collectors.toList());
    }

    // متدهای جدید برای مدیریت کامنت‌ها
    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public List<Comment> getCommentsForSong(String songId) {
        return comments.stream()
                .filter(comment -> comment.getSong().getId().equals(songId))
                .sorted((c1, c2) -> c2.getCreatedAt().compareTo(c1.getCreatedAt()))
                .collect(Collectors.toList());
    }
}