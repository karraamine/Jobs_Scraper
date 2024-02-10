package model;

public class Skill {
    private int idOffre;
    private String skillName;
    private String skillType;

    // Getter and Setter methods for all attributes
    public Skill(String skillName, String skillType){
        this.skillName = skillName;
        this.skillType = skillType;
    }
    public int getIdOffre() {
        return idOffre;
    }

    public void setIdOffre(int idOffre) {
        this.idOffre = idOffre;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getSkillType() {
        return skillType;
    }

    public void setSkillType(String skillType) {
        this.skillType = skillType;
    }

    // Other methods as needed
}


