package com.hfad.taskmanagement.dto;

import java.io.Serializable;

public class CurrentTaskIsReopenDTO implements Serializable {

    private String txtTaskId, txtTaskName, txtAssignDate, txtStartDate, txtEndDate, txtAssignee, status, feedback, createdFeedbackDate, rate;

    public CurrentTaskIsReopenDTO() {
    }

    public CurrentTaskIsReopenDTO(String txtTaskId, String txtTaskName, String txtAssignDate, String txtStartDate, String txtEndDate, String txtAssignee, String status, String feedback, String createdFeedbackDate, String rate) {
        this.txtTaskId = txtTaskId;
        this.txtTaskName = txtTaskName;
        this.txtAssignDate = txtAssignDate;
        this.txtStartDate = txtStartDate;
        this.txtEndDate = txtEndDate;
        this.txtAssignee = txtAssignee;
        this.status = status;
        this.feedback = feedback;
        this.createdFeedbackDate = createdFeedbackDate;
        this.rate = rate;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getCreatedFeedbackDate() {
        return createdFeedbackDate;
    }

    public void setCreatedFeedbackDate(String createdFeedbackDate) {
        this.createdFeedbackDate = createdFeedbackDate;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTxtTaskName() {
        return txtTaskName;
    }

    public void setTxtTaskName(String txtTaskName) {
        this.txtTaskName = txtTaskName;
    }

    public String getTxtAssignDate() {
        return txtAssignDate;
    }

    public void setTxtAssignDate(String txtAssignDate) {
        this.txtAssignDate = txtAssignDate;
    }

    public String getTxtStartDate() {
        return txtStartDate;
    }

    public void setTxtStartDate(String txtStartDate) {
        this.txtStartDate = txtStartDate;
    }

    public String getTxtEndDate() {
        return txtEndDate;
    }

    public void setTxtEndDate(String txtEndDate) {
        this.txtEndDate = txtEndDate;
    }

    public String getTxtAssignee() {
        return txtAssignee;
    }

    public String getTxtTaskId() {
        return txtTaskId;
    }

    public void setTxtTaskId(String txtTaskId) {
        this.txtTaskId = txtTaskId;
    }

    public void setTxtAssignee(String txtAssignee) {
        this.txtAssignee = txtAssignee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
