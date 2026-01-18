public abstract class MediaItem {
    private int id;
    private String title;
    private int durSeconds;

    public MediaItem(String title, int durSeconds) {
        this.title = title;
        this.durSeconds = durSeconds;
    }

    public MediaItem(int id, String title, int durSeconds) {
        this.id = id;
        this.title = title;
        this.durSeconds = durSeconds;
    }

    public abstract void play();

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public int getDurSeconds() { return durSeconds; }
}