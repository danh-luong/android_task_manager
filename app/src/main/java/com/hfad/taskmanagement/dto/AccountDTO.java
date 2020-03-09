package com.hfad.taskmanagement.dto;

import java.io.Serializable;

public class AccountDTO implements Serializable {

    private String username, password;
    private int roleId;

    public AccountDTO() {
    }

    public AccountDTO(String username, String password, int roleId) {
        this.username = username;
        this.password = password;
        this.roleId = roleId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
