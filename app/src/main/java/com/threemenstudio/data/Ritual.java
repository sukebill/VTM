package com.threemenstudio.data;

public class Ritual {

    private int id;
    private String name;
    private String description;
    private String system;
    private int level;
    private String side;

    public Ritual(int id, String name, String description, String system, int level, String side) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.system = system;
        this.level = level;
        this.side = side;
    }

    public Ritual() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }
}
