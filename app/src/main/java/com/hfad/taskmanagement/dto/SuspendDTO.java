package com.hfad.taskmanagement.dto;

import java.io.Serializable;

public class SuspendDTO implements Serializable {

    private String taskId, userName;

    public SuspendDTO(String taskId, String userName) {
        this.taskId = taskId;
        this.userName = userName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
