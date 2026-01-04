import java.util.Objects;

public class Artist {
    private String name;
    private String genre;

    public Artist(String name, String genre) {
        this.name = name;
        this.genre = genre;
    }

    public String getName() { return name; }
    public String getGenre() { return genre; }

    @Override
    public String toString() { return name + " [" + genre + "]"; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Artist)) return false;
        Artist artist = (Artist) o;
        return Objects.equals(name, artist.name) && Objects.equals(genre, artist.genre);
    }

    @Override
    public int hashCode() { return Objects.hash(name, genre); }
}