package com.music;

import java.util.*;

public class Playlist {
    private List<Song> songs = new ArrayList<>();

    public void addSong(Song song) {
        songs.add(song);
    }

    public List<Song> getSongs() {
        songs.sort(Comparator.comparingInt(MediaItem::getDurSeconds));
        return songs;
    }
}