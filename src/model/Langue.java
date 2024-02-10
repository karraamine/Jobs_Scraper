package model;


public class Langue {
    private int idOffre;
    private String langueName;
    private String level;

    // Getter and Setter methods for all attributes

    public Langue(){

    }
    public Langue(String langueName, String level){
        this.langueName = langueName;
        this.level = level;
    }
    public int getIdOffre() {
        return idOffre;
    }

    public void setIdOffre(int idOffre) {
        this.idOffre = idOffre;
    }

    public String getLangueName() {
        return langueName;
    }

    public void setLangueName(String langueName) {
        this.langueName = langueName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    // Other methods as needed
}


