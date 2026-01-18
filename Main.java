import java.util.Scanner;

public class Main {
    private static Playlist myPlaylist = new Playlist("❤️Favourites");
    private static MusicDAO dao = new MusicDAO();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);


        refreshPlaylist();

        while (true) {
            System.out.println("\nMenu: 1.Show | 2.Add | 3.Delete | 4.Sort (Dur) | 0.Quit");
            System.out.print("> ");
            String input = sc.nextLine();

            switch (input) {
                case "1" -> myPlaylist.showAll();
                case "2" -> {
                    System.out.print("Name: "); String t = sc.nextLine();
                    System.out.print("Length (sec): "); int d = Integer.parseInt(sc.nextLine());
                    System.out.print("Artist: "); String a = sc.nextLine();

                    Song newSong = new Song(t, d, new Artist(a, "Unknown"));
                    dao.saveSong(newSong);
                    refreshPlaylist();
                }
                case "3" -> {
                    System.out.print("Enter ID to delete: ");
                    try {
                        int id = Integer.parseInt(sc.nextLine());
                        dao.deleteMedia(id);
                        refreshPlaylist();
                    } catch (Exception e) { System.out.println("Invalid ID format."); }
                }
                case "4" -> {
                    myPlaylist.sortByDuration();
                    myPlaylist.showAll();
                }
                case "0" -> { return; }
                default -> System.out.println("Unknown command.");
            }
        }
    }


    private static void refreshPlaylist() {
        Playlist fresh = new Playlist("❤️Favourites");
        for (Song s : dao.getAllSongs()) {
            fresh.addSong(s);
        }
        myPlaylist = fresh;
    }
}