import java.util.*;

public class Playlist {
    private String name;
    private List<Song> songs = new ArrayList<>();

    public Playlist(String name) { this.name = name; }

    public void addSong(Song song) { songs.add(song); }

    public void showAll() {
        System.out.println("\n--- Плейлист: " + name + " ---");
        if (songs.isEmpty()) System.out.println("Пусто.");
        else songs.forEach(System.out::println);
    }

    public void sortByTitle() {
        songs.sort(Comparator.comparing(Song::getTitle));
        System.out.println("Сортировка по названию завершена.");
    }

    public void sortByDuration() {
        songs.sort(Comparator.comparingInt(Song::getDurSeconds));
        System.out.println("Сортировка по длительности завершена.");
    }

    public void findByArtist(String artistName) {
        System.out.println("Результаты поиска для: " + artistName);
        songs.stream()
                .filter(s -> s.toString().toLowerCase().contains(artistName.toLowerCase()))
                .forEach(System.out::println);
    }
}