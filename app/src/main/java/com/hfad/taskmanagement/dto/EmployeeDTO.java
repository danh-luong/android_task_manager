package com.hfad.taskmanagement.dto;

import java.io.Serializable;

public class EmployeeDTO implements Serializable {

    private String id, username, name, email, phone;

    public EmployeeDTO() {
    }

    public EmployeeDTO(String id, String username, String name, String email, String phone) {
        this.username = username;
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
