package com.threemenstudio.data;


public class Ability {

    private int id;
    private String title;
    private String path;
    private String description;
    private String system;
    private int level;
    private boolean child;

    public Ability() {
    }

    public Ability(int id, String title, String path, String description, String system, int level, boolean child) {
        this.id = id;
        this.title = title;
        this.path = path;
        this.description = description;
        this.system = system;
        this.level = level;
        this.child = child;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isChild() {
        return child;
    }

    public void setChild(boolean child) {
        this.child = child;
    }
}
