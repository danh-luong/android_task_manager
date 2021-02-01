package com.hfad.taskmanagement.dto;

import java.io.Serializable;

public class ScanDTO implements Serializable {

    private String employeeId, userNameManager;

    public ScanDTO() {
    }

    public ScanDTO(String employeeId, String userNameManager) {
        this.employeeId = employeeId;
        this.userNameManager = userNameManager;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getUserNameManager() {
        return userNameManager;
    }

    public void setUserNameManager(String userNameManager) {
        this.userNameManager = userNameManager;
    }
}
