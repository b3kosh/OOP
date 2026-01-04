public abstract class MediaItem {
    private String title;
    private int durSeconds;

    public MediaItem(String title, int durSeconds) {
        this.title = title;
        this.durSeconds = durSeconds;
    }

    public abstract void play(); // Абстрактный метод (Полиморфизм)

    public String getTitle() { return title; }
    public int getDurSeconds() { return durSeconds; }
}