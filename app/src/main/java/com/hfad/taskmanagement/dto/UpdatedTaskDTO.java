package com.hfad.taskmanagement.dto;

import java.io.Serializable;

public class UpdatedTaskDTO implements Serializable {

    public String id, taskName, descriptionTask, endDate;

    public UpdatedTaskDTO(String id, String taskName, String descriptionTask, String endDate) {
        this.id = id;
        this.taskName = taskName;
        this.descriptionTask = descriptionTask;
        this.endDate = endDate;
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

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
