package com.threemenstudio.data;

public class Sorcery {

    private int id;
    private String name;
    private String desc;
    private String social;
    private String ritualPractice;
    private String rituals;

    public Sorcery(int id, String name, String desc, String social, String ritualPractice, String rituals) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.social = social;
        this.ritualPractice = ritualPractice;
        this.rituals = rituals;
    }

    public Sorcery() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSocial() {
        return social;
    }

    public void setSocial(String social) {
        this.social = social;
    }

    public String getRitualPractice() {
        return ritualPractice;
    }

    public void setRitualPractice(String ritualPractice) {
        this.ritualPractice = ritualPractice;
    }

    public String getRituals() {
        return rituals;
    }

    public void setRituals(String rituals) {
        this.rituals = rituals;
    }
}
