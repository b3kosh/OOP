import java.util.Objects;

public class Song {
    private String title;
    private int durSeconds;
    private final Artist artist;

    public Song(String title, int durSeconds, Artist artist) {
        this.title = title;
        this.durSeconds = durSeconds;
        this.artist = artist;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDurSeconds() {
        return durSeconds;
    }

    public void setDurSeconds(int durSeconds) {
        this.durSeconds = durSeconds;
    }

    public Artist getArtist() {
        return artist;
    }


    public void play() {
        System.out.println("Воспроизводится: " + title + " от " + artist.getName());
    }

    @Override
    public String toString() {
        return "Песня: \"" + title + "\" (" + durSeconds + " сек) - " + artist.getName();
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        Song otherSong = (Song) obj;

        return durSeconds == otherSong.durSeconds &&
                Objects.equals(title, otherSong.title) &&
                Objects.equals(artist, otherSong.artist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, durSeconds, artist);
    }
}