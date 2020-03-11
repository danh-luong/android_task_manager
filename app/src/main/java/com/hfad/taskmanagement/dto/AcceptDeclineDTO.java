package com.hfad.taskmanagement.dto;

import java.io.Serializable;

public class AcceptDeclineDTO implements Serializable {

    private String taskId, userId;

    public AcceptDeclineDTO() {
    }

    public AcceptDeclineDTO(String taskId, String userId) {
        this.taskId = taskId;
        this.userId = userId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
