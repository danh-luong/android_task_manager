package com.hfad.taskmanagement.dto;

import java.io.Serializable;

public class TaskAcceptDeclineDTO implements Serializable {

    private String taskId, feedback, rate;

    public TaskAcceptDeclineDTO(String taskId, String feedback, String rate) {
        this.taskId = taskId;
        this.feedback = feedback;
        this.rate = rate;
    }

    public TaskAcceptDeclineDTO() {
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
