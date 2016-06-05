package com.threemenstudio.data;

public class Discipline {

    private int id;
    private String title;
    private String description;
    private boolean common;
    private boolean rare;
    private String bonusDescription;
    private String officialAbilities;
    private String rituals;
    private String ritualsSystem;
    private String subtitle;

    public Discipline() {
    }

    public Discipline(int id, String title, String description, boolean common, boolean rare,
                      String bonusDescription, String officialAbilities, String rituals,
                      String ritualsSystem, String subtitle) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.common = common;
        this.rare = rare;
        this.bonusDescription = bonusDescription;
        this.officialAbilities = officialAbilities;
        this.rituals = rituals;
        this.ritualsSystem = ritualsSystem;
        this.subtitle = subtitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCommon() {
        return common;
    }

    public void setCommon(boolean common) {
        this.common = common;
    }

    public boolean isRare() {
        return rare;
    }

    public void setRare(boolean rare) {
        this.rare = rare;
    }

    public String getBonusDescription() {
        return bonusDescription;
    }

    public void setBonusDescription(String bonusDescription) {
        this.bonusDescription = bonusDescription;
    }

    public String getOfficialAbilities() {
        return officialAbilities;
    }

    public void setOfficialAbilities(String officialAbilities) {
        this.officialAbilities = officialAbilities;
    }

    public String getRituals() {
        return rituals;
    }

    public void setRituals(String rituals) {
        this.rituals = rituals;
    }

    public String getRitualsSystem() {
        return ritualsSystem;
    }

    public void setRitualsSystem(String ritualsSystem) {
        this.ritualsSystem = ritualsSystem;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}
