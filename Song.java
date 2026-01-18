import java.util.Objects;

public class Song extends MediaItem {
    private Artist artist;

    public Song(String title, int durSeconds, Artist artist) {
        super(title, durSeconds);
        this.artist = artist;
    }

    @Override
    public void play() {
        System.out.println("Playing: " + getTitle() + " - " + artist.getName());
    }

    @Override
    public String toString() {
        return String.format("%-20s | %-15s | %d sec", getTitle(), artist.getName(), getDurSeconds());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Song)) return false;
        Song song = (Song) o;
        return getDurSeconds() == song.getDurSeconds() &&
                Objects.equals(getTitle(), song.getTitle()) &&
                Objects.equals(artist, song.artist);
    }

    @Override
    public int hashCode() { return Objects.hash(getTitle(), getDurSeconds(), artist); }
}