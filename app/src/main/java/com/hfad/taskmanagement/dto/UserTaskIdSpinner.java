package com.hfad.taskmanagement.dto;

import java.io.Serializable;

public class UserTaskIdSpinner implements Serializable {

    private String username, taskId;

    public UserTaskIdSpinner(String username, String taskId) {
        this.username = username;
        this.taskId = taskId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
