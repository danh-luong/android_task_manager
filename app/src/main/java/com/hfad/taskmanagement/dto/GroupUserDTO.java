package com.hfad.taskmanagement.dto;

import java.io.Serializable;

public class GroupUserDTO implements Serializable {

    private String groupId, employeeId, employeeName, roleName, roleId;

    public GroupUserDTO() {
    }

    public GroupUserDTO(String groupId, String employeeId, String employeeName, String roleName, String roleId) {
        this.groupId = groupId;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.roleName = roleName;
        this.roleId = roleId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
