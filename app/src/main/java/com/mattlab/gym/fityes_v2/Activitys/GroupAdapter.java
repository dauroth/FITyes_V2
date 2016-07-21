package com.mattlab.gym.fityes_v2.Activitys;

/**
 * Created by EXT on 2016. 06. 21..
 */

public class GroupAdapter {
    String group_id;
    String name;
    String desc;
    int photoId;

    public GroupAdapter(String group_id, String name, String desc, int photoId) {
        this.group_id = group_id;
        this.name = name;
        this.desc = desc;
        this.photoId = photoId;
    }
}