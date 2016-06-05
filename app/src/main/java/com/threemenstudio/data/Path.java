package com.threemenstudio.data;


public class Path {

    private int id;
    private String name;
    private String attribute;
    private String description;
    private String system;
    private String official;
    private String price;

    public Path() {
    }

    public Path(int id, String name, String attribute, String description, String system,
                String official, String price) {
        this.id = id;
        this.name = name;
        this.attribute = attribute;
        this.description = description;
        this.system = system;
        this.official = official;
        this.price = price;
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

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
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

    public String getOfficial() {
        return official;
    }

    public void setOfficial(String official) {
        this.official = official;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
