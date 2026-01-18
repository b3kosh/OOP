import java.util.*;

public class Playlist {
    private String name;
    private List<Song> songs = new ArrayList<>();

    public Playlist(String name) { this.name = name; }

    public void addSong(Song song) { songs.add(song); }

    public void showAll() {
        System.out.println("\n--- Playlist: " + name + " ---");
        if (songs.isEmpty()) System.out.println("Empty.");
        else songs.forEach(System.out::println);
    }

    public void sortByTitle() {
        songs.sort(Comparator.comparing(Song::getTitle));
        System.out.println("The sorting by names is finished.");
    }

    public void sortByDuration() {
        songs.sort(Comparator.comparingInt(Song::getDurSeconds));
        System.out.println("The sorting by length is finished.");
    }

    public void findByArtist(String artistName) {
        System.out.println("Results for search: " + artistName);
        songs.stream()
                .filter(s -> s.toString().toLowerCase().contains(artistName.toLowerCase()))
                .forEach(System.out::println);
    }
}