package com.hfad.taskmanagement.dto;

import java.io.Serializable;

public class TaskDetailDTO implements Serializable {
    public String taskName, descriptionTask, startDate, endDate, createdDate, userCreation, status;

    public TaskDetailDTO(String taskName, String descriptionTask, String startDate, String endDate, String createdDate, String userCreation, String status) {
        this.taskName = taskName;
        this.descriptionTask = descriptionTask;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdDate = createdDate;
        this.userCreation = userCreation;
        this.status = status;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescriptionTask() {
        return descriptionTask;
    }

    public void setDescriptionTask(String descriptionTask) {
        this.descriptionTask = descriptionTask;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUserCreation() {
        return userCreation;
    }

    public void setUserCreation(String userCreation) {
        this.userCreation = userCreation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
