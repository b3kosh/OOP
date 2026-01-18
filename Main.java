import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Playlist myPlaylist = new Playlist("❤️Favourites");

        Artist art1 = new Artist("The Weeknd", "R&B");
        myPlaylist.addSong(new Song("Blinding Lights", 200, art1));
        myPlaylist.addSong(new Song("Save Your Tears", 215, art1));

        while (true) {
            System.out.println("\nMenu: 1.Show | 2.Add | 3.Sort(A-Z) | 4.Sort(length) | 5.🧐 | 0.Quit");
            System.out.print("> ");
            String input = sc.nextLine();

            switch (input) {
                case "1" -> myPlaylist.showAll();
                case "2" -> {
                    System.out.print("Name: "); String t = sc.nextLine();
                    System.out.print("Length (sec): "); int d = Integer.parseInt(sc.nextLine());
                    System.out.print("Artist: "); String a = sc.nextLine();
                    myPlaylist.addSong(new Song(t, d, new Artist(a, "Unknown")));
                }
                case "3" -> myPlaylist.sortByTitle();
                case "4" -> myPlaylist.sortByDuration();
                case "5" -> {
                    System.out.print("Artist's name: ");
                    myPlaylist.findByArtist(sc.nextLine());
                }
                case "0" -> { System.out.println("Bye!"); return; }
                default -> System.out.println("Error.");
            }
        }
    }
}

