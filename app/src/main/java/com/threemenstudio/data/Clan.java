package com.threemenstudio.data;

public class Clan {

    private int id;
    private String clan;
    private String caste;
    private String littleDesc;
    private String nickname;
    private String desc;
    private String sect;
    private String appearance;
    private String haven;
    private String background;
    private String characterCreation;
    private String weakness;
    private String organization;
    private String bloodlines;
    private String subtitle;

    public Clan() {
    }

    public Clan(int id, String clan, String caste, String littleDesc, String nickname, String desc,
                String sect, String appearance, String haven, String background, String characterCreation,
                String weakness, String organization, String bloodlines, String subtitle) {
        this.id = id;
        this.clan = clan;
        this.caste = caste;
        this.littleDesc = littleDesc;
        this.nickname = nickname;
        this.desc = desc;
        this.sect = sect;
        this.appearance = appearance;
        this.haven = haven;
        this.background = background;
        this.characterCreation = characterCreation;
        this.weakness = weakness;
        this.organization = organization;
        this.bloodlines = bloodlines;
        this.subtitle = subtitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClan() {
        return clan;
    }

    public void setClan(String clan) {
        this.clan = clan;
    }

    public String getCaste() {
        return caste;
    }

    public void setCaste(String caste) {
        this.caste = caste;
    }

    public String getLittleDesc() {
        return littleDesc;
    }

    public void setLittleDesc(String littleDesc) {
        this.littleDesc = littleDesc;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSect() {
        return sect;
    }

    public void setSect(String sect) {
        this.sect = sect;
    }

    public String getAppearance() {
        return appearance;
    }

    public void setAppearance(String appearance) {
        this.appearance = appearance;
    }

    public String getHaven() {
        return haven;
    }

    public void setHaven(String haven) {
        this.haven = haven;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getCharacterCreation() {
        return characterCreation;
    }

    public void setCharacterCreation(String characterCreation) {
        this.characterCreation = characterCreation;
    }

    public String getWeakness() {
        return weakness;
    }

    public void setWeakness(String weakness) {
        this.weakness = weakness;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getBloodlines() {
        return bloodlines;
    }

    public void setBloodlines(String bloodlines) {
        this.bloodlines = bloodlines;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}
