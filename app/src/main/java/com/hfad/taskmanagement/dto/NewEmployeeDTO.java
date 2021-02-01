package com.hfad.taskmanagement.dto;

import java.io.Serializable;

public class NewEmployeeDTO implements Serializable {

    private String txtName, txtStatus, txtEmail, txtAddress, txtPhone, txtRoleId, txtGroupId, txtUserName, txtPassword;

    public NewEmployeeDTO(String txtName, String txtStatus, String txtEmail, String txtAddress, String txtPhone, String txtRoleId, String txtGroupId, String txtUserName, String txtPassword) {
        this.txtName = txtName;
        this.txtStatus = txtStatus;
        this.txtEmail = txtEmail;
        this.txtAddress = txtAddress;
        this.txtPhone = txtPhone;
        this.txtRoleId = txtRoleId;
        this.txtGroupId = txtGroupId;
        this.txtUserName = txtUserName;
        this.txtPassword = txtPassword;
    }

    public String getTxtUserName() {
        return txtUserName;
    }

    public void setTxtUserName(String txtUserName) {
        this.txtUserName = txtUserName;
    }

    public String getTxtPassword() {
        return txtPassword;
    }

    public void setTxtPassword(String txtPassword) {
        this.txtPassword = txtPassword;
    }

    public String getTxtName() {
        return txtName;
    }

    public void setTxtName(String txtName) {
        this.txtName = txtName;
    }

    public String getTxtStatus() {
        return txtStatus;
    }

    public void setTxtStatus(String txtStatus) {
        this.txtStatus = txtStatus;
    }

    public String getTxtEmail() {
        return txtEmail;
    }

    public void setTxtEmail(String txtEmail) {
        this.txtEmail = txtEmail;
    }

    public String getTxtAddress() {
        return txtAddress;
    }

    public void setTxtAddress(String txtAddress) {
        this.txtAddress = txtAddress;
    }

    public String getTxtPhone() {
        return txtPhone;
    }

    public void setTxtPhone(String txtPhone) {
        this.txtPhone = txtPhone;
    }

    public String getTxtRoleId() {
        return txtRoleId;
    }

    public void setTxtRoleId(String txtRoleId) {
        this.txtRoleId = txtRoleId;
    }

    public String getTxtGroupId() {
        return txtGroupId;
    }

    public void setTxtGroupId(String txtGroupId) {
        this.txtGroupId = txtGroupId;
    }
}

