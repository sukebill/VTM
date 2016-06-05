package com.threemenstudio.data;

public class ComboDiscipline {

    private int id;
    private String name;
    private String desc;
    private String system;
    private int exp;
    private String first;
    private String second;
    private String third;

    public ComboDiscipline(int id, String name, String desc, String system, int exp, String first,
                           String second, String third) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.system = system;
        this.exp = exp;
        this.first = first;
        this.second = second;
        this.third = third;
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

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }
}
