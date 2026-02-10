package com.music;

public abstract class MediaItem {
    private int id;
    private String title;
    private int durSeconds;

    public MediaItem() {}
    public MediaItem(int id, String title, int durSeconds) {
        this.id = id;
        this.title = title;
        this.durSeconds = durSeconds;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public int getDurSeconds() { return durSeconds; }
    public void setDurSeconds(int durSeconds) { this.durSeconds = durSeconds; }
}