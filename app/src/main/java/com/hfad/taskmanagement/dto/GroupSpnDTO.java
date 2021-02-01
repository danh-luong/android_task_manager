package com.hfad.taskmanagement.dto;

import java.io.Serializable;

public class GroupSpnDTO implements Serializable {

    private String groupId, name;

    public GroupSpnDTO() {
    }

    public GroupSpnDTO(String groupId, String name) {
        this.groupId = groupId;
        this.name = name;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
