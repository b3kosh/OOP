import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Playlist myPlaylist = new Playlist("Избранное");

        // Тестовые данные
        Artist art1 = new Artist("The Weeknd", "R&B");
        myPlaylist.addSong(new Song("Blinding Lights", 200, art1));
        myPlaylist.addSong(new Song("Save Your Tears", 215, art1));

        while (true) {
            System.out.println("\nМеню: 1.Показать | 2.Добавить | 3.Сортировать(А-Я) | 4.Сортировать(Время) | 5.Поиск | 0.Выход");
            System.out.print("> ");
            String input = sc.nextLine();

            switch (input) {
                case "1" -> myPlaylist.showAll();
                case "2" -> {
                    System.out.print("Название: "); String t = sc.nextLine();
                    System.out.print("Длительность (сек): "); int d = Integer.parseInt(sc.nextLine());
                    System.out.print("Артист: "); String a = sc.nextLine();
                    myPlaylist.addSong(new Song(t, d, new Artist(a, "Unknown")));
                }
                case "3" -> myPlaylist.sortByTitle();
                case "4" -> myPlaylist.sortByDuration();
                case "5" -> {
                    System.out.print("Имя артиста: ");
                    myPlaylist.findByArtist(sc.nextLine());
                }
                case "0" -> { System.out.println("Пока!"); return; }
                default -> System.out.println("Неверный ввод.");
            }
        }
    }
}