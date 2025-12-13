import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class Playlist {
    private String name;
    private final List<Song> songs;
    private String description;
    private int totalDurationSeconds;

    public Playlist(String name) {
        this(name, "Нет описания");
    }

    public Playlist(String name, String description) {
        this.name = name;
        this.description = description;
        this.songs = new ArrayList<>();
        this.totalDurationSeconds = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Song> getSongs() {
        return new ArrayList<>(songs);
    }

    public int getTotalDurationSeconds() {
        return totalDurationSeconds;
    }

    public int getSongCount() {
        return songs.size();
    }

    public void addSong(Song song) {
        if (song == null) {
            System.out.println("Ошибка: Песня не может быть null");
            return;
        }

        this.songs.add(song);
        this.totalDurationSeconds += song.getDurSeconds();
        System.out.println("✓ Добавлено: \"" + song.getTitle() +
                "\" в плейлист \"" + name + "\"");
    }

    public void addSongs(List<Song> songsToAdd) {
        if (songsToAdd == null || songsToAdd.isEmpty()) {
            System.out.println("Список песен пуст");
            return;
        }

        for (Song song : songsToAdd) {
            addSong(song);
        }
    }

    public boolean removeSong(String songTitle) {
        Iterator<Song> iterator = songs.iterator();
        while (iterator.hasNext()) {
            Song song = iterator.next();
            if (song.getTitle().equalsIgnoreCase(songTitle)) {
                totalDurationSeconds -= song.getDurSeconds();
                iterator.remove();
                System.out.println("✗ Удалено: \"" + songTitle +
                        "\" из плейлиста \"" + name + "\"");
                return true;
            }
        }
        System.out.println("Песня \"" + songTitle + "\" не найдена в плейлисте");
        return false;
    }

    public boolean removeSong(Song songToRemove) {
        if (songToRemove == null) return false;

        boolean removed = songs.remove(songToRemove);
        if (removed) {
            totalDurationSeconds -= songToRemove.getDurSeconds();
            System.out.println("✗ Удалено: \"" + songToRemove.getTitle() +
                    "\" из плейлиста \"" + name + "\"");
        }
        return removed;
    }

    public void clearPlaylist() {
        System.out.println("Очистка плейлиста \"" + name + "\" (" +
                songs.size() + " песен)");
        songs.clear();
        totalDurationSeconds = 0;
    }

    public boolean containsSong(Song song) {
        return songs.contains(song);
    }

    public boolean containsSong(String songTitle) {
        for (Song song : songs) {
            if (song.getTitle().equalsIgnoreCase(songTitle)) {
                return true;
            }
        }
        return false;
    }

    public void playPlaylist() {
        if (songs.isEmpty()) {
            System.out.println("Плейлист \"" + name + "\" пуст!");
            return;
        }

        System.out.println("\nНачинается воспроизведение плейлиста: " +
                name + " (" + songs.size() + " треков, " +
                formatDuration(totalDurationSeconds) + ")");
        System.out.println("Описание: " + description);
        System.out.println("-".repeat(50));

        int trackNumber = 1;
        for (Song song : songs) {
            System.out.print(trackNumber + ". ");
            song.play();
            trackNumber++;
        }

        System.out.println("-".repeat(50));
        System.out.println("Конец плейлиста: " + name);
    }

    public void shufflePlaylist() {
        if (songs.size() > 1) {
            java.util.Collections.shuffle(songs);
            System.out.println("Плейлист \"" + name + "\" перемешан");
        }
    }

    public List<Song> findSongsByArtist(String artistName) {
        List<Song> result = new ArrayList<>();
        for (Song song : songs) {
            if (song.getArtist().getName().equalsIgnoreCase(artistName)) {
                result.add(song);
            }
        }
        return result;
    }

    private String formatDuration(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    @Override
    public String toString() {
        return "Плейлист: \"" + name + "\" | " +
                songs.size() + " треков | " +
                formatDuration(totalDurationSeconds) +
                " | Описание: " + description;
    }

    public void printDetailedInfo() {
        System.out.println("ДЕТАЛЬНАЯ ИНФОРМАЦИЯ О ПЛЕЙЛИСТЕ");
        System.out.println("Название: " + name);
        System.out.println("Описание: " + description);
        System.out.println("Количество треков: " + songs.size());
        System.out.println("Общая длительность: " + formatDuration(totalDurationSeconds));

        if (!songs.isEmpty()) {
            System.out.println("\nСписок треков:");
            int index = 1;
            for (Song song : songs) {
                System.out.printf("%2d. %-30s %-20s %s%n",
                        index++,
                        song.getTitle(),
                        song.getArtist().getName(),
                        formatDuration(song.getDurSeconds())); // ИСПРАВЛЕНО
            }
        }
    }

    public boolean isSamePlaylist(Playlist other) {
        if (other == null) return false;
        return this.name.equals(other.name) &&
                this.songs.equals(other.songs);
    }
}