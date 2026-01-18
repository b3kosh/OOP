import java.util.*;

public class Playlist {
    private List<Song> songs = new ArrayList<>();

    public void addSong(Song song) { songs.add(song); }
    public List<Song> getSongs() { return songs; }

    public void showAll() {
        System.out.println("\n--- My Playlist ---");
        if (songs.isEmpty()) System.out.println("Empty.");
        for (int i = 0; i < songs.size(); i++) {
            System.out.println((i + 1) + ". " + songs.get(i));
        }
    }

    public void sortByDuration() {
        songs.sort(Comparator.comparingInt(MediaItem::getDurSeconds));
    }
}