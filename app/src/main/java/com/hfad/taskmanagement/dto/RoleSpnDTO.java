package com.hfad.taskmanagement.dto;

import java.io.Serializable;

public class RoleSpnDTO implements Serializable {

    private String roleId, roleName;

    public RoleSpnDTO() {
    }

    public RoleSpnDTO(String roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
