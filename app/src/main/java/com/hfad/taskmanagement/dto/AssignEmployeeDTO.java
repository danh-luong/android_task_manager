package com.hfad.taskmanagement.dto;

import java.io.Serializable;

public class AssignEmployeeDTO implements Serializable {

    private String groupId, userId, roleId;

    public AssignEmployeeDTO(String taskId, String userId, String roleId) {
        this.groupId = taskId;
        this.userId = userId;
        this.roleId = roleId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String taskId) {
        this.groupId = taskId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
