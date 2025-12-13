public class Artist {
    private String name;
    private String genre;

    public Artist(String name, String genre){
        this.name = name;
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void perform() {
        System.out.println(name + " выступает в жанре " + genre + ".");
    }

    @Override
    public String toString(){
        return "Исполнитель: \" + name + \" (\" + genre + \")";
    }
}
