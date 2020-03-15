package com.hfad.taskmanagement.dto;

import java.io.Serializable;

public class InsertNewTaskDTO implements Serializable {

    private String taskId, username;

    public InsertNewTaskDTO() {
    }

    public InsertNewTaskDTO(String taskId, String username) {
        this.taskId = taskId;
        this.username = username;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
