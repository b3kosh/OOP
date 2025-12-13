import java.util.Objects;

public class Artist {
    private String name;
    private String genre;

    public Artist(String name, String genre) {
        this.name = name;
        this.genre = genre;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public void perform() {
        System.out.println(name + " выступает в жанре " + genre + ".");
    }

    @Override
    public String toString() {
        return "Исполнитель: " + name + " (" + genre + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Artist other = (Artist) obj;
        return Objects.equals(name, other.name) &&
                Objects.equals(genre, other.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, genre);
    }
}