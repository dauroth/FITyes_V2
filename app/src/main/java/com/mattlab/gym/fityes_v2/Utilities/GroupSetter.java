package com.mattlab.gym.fityes_v2.Utilities;

/**
 * Created by EXT on 2016. 06. 21..
 */

public class GroupSetter {

    private String name, desc, number;

    public GroupSetter(String name, String desc, String number) {
        this.setName(name);
        this.setDesc(desc);
        this.setNumber(number);
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
