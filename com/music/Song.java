package com.music;

public class Song extends MediaItem {
    private Artist artist;
    private String surname;

    public Song() { super(); }
    public Song(int id, String title, int durSeconds, Artist artist, String surname) {
        super(id, title, durSeconds);
        this.artist = artist;
        this.surname = surname;
    }

    public Artist getArtist() { return artist; }
    public void setArtist(Artist artist) { this.artist = artist; }
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }
}