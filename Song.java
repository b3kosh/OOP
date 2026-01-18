public class Song extends MediaItem {
    private Artist artist;

    public Song(String title, int durSeconds, Artist artist) {
        super(title, durSeconds);
        this.artist = artist;
    }

    public Artist getArtist() { return artist; }

    @Override
    public void play() {
        System.out.println("Playing: " + getTitle());
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Song: %-20s | Artist: %s",
                getId(), getTitle(), artist.getName());
    }
}