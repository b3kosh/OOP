public class Main {
    public static void main(String[] args) {
        Artist artist1 = new Artist("The Weeknd", "R&B");
        Artist artist2 = new Artist("Dua Lipa", "Pop");
        Artist artist3 = new Artist("Imagine Dragons", "Rock");

        Song song1 = new Song("Blinding Lights", 200, artist1);
        Song song2 = new Song("Levitating", 203, artist2);
        Song song3 = new Song("Believer", 204, artist3);
        Song song4 = new Song("Save Your Tears", 215, artist1);

        Playlist playlist = new Playlist("Hits 2023");

        playlist.addSong(song1);
        playlist.addSong(song2);
        playlist.addSong(song3);
        playlist.addSong(song4);

        System.out.println(playlist);

        playlist.playPlaylist();

        Song song5 = new Song("Blinding Lights", 200, artist1);
        System.out.println("Сравнение: " + song1.equals(song5));

        playlist.removeSong("Believer");

        playlist.playPlaylist();
    }
}