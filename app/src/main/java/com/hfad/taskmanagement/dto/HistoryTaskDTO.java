package com.hfad.taskmanagement.dto;

import java.io.Serializable;

public class HistoryTaskDTO implements Serializable {

    private String txtTaskName, txtAssignDate, txtAssignee, txtStatus;
    private String txtSupendDate, txtSuspendBy;

    public HistoryTaskDTO(String txtTaskName, String txtAssignDate, String txtAssignee, String txtStatus, String txtSupendDate, String txtSuspendBy) {
        this.txtTaskName = txtTaskName;
        this.txtAssignDate = txtAssignDate;
        this.txtAssignee = txtAssignee;
        this.txtStatus = txtStatus;
        this.txtSupendDate = txtSupendDate;
        this.txtSuspendBy = txtSuspendBy;
    }

    public HistoryTaskDTO(String txtTaskName, String txtAssignDate, String txtAssignee, String txtStatus) {
        this.txtTaskName = txtTaskName;
        this.txtAssignDate = txtAssignDate;
        this.txtAssignee = txtAssignee;
        this.txtStatus = txtStatus;
    }

    public String getTxtSupendDate() {
        return txtSupendDate;
    }

    public void setTxtSupendDate(String txtSupendDate) {
        this.txtSupendDate = txtSupendDate;
    }

    public String getTxtSuspendBy() {
        return txtSuspendBy;
    }

    public void setTxtSuspendBy(String txtSuspendBy) {
        this.txtSuspendBy = txtSuspendBy;
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

    public String getTxtAssignee() {
        return txtAssignee;
    }

    public void setTxtAssignee(String txtAssignee) {
        this.txtAssignee = txtAssignee;
    }

    public String getTxtStatus() {
        return txtStatus;
    }

    public void setTxtStatus(String txtStatus) {
        this.txtStatus = txtStatus;
    }
}
