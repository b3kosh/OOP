import java.util.Scanner;

public class Main {
    private static Playlist myPlaylist = new Playlist();
    private static MusicDAO dao = new MusicDAO();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        refresh();

        while (true) {
            System.out.println("\n1. Show | 2. Add | 3. Delete | 4. Sort | 0. Exit");
            System.out.print("> ");
            String cmd = sc.nextLine();

            switch (cmd) {
                case "1" -> myPlaylist.showAll();
                case "2" -> {
                    System.out.print("Title: "); String t = sc.nextLine();
                    System.out.print("Sec: "); int d = Integer.parseInt(sc.nextLine());
                    System.out.print("Artist: "); String a = sc.nextLine();
                    // Теперь Artist создается только с одним параметром (имя)
                    dao.saveSong(new Song(t, d, new Artist(a)));
                    refresh();
                }
                case "3" -> {
                    myPlaylist.showAll();
                    System.out.print("Enter number to delete: ");
                    try {
                        int num = Integer.parseInt(sc.nextLine());
                        if (num > 0 && num <= myPlaylist.getSongs().size()) {
                            int dbId = myPlaylist.getSongs().get(num - 1).getId();
                            dao.deleteMedia(dbId);
                            refresh();
                        }
                    } catch (Exception e) { System.out.println("Invalid input."); }
                }
                case "4" -> {
                    myPlaylist.sortByDuration();
                    myPlaylist.showAll();
                }
                case "0" -> { return; }
            }
        }
    }

    private static void refresh() {
        myPlaylist = new Playlist();
        for (Song s : dao.getAllSongs()) {
            myPlaylist.addSong(s);
        }
    }
}