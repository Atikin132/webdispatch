package com.example.dto;

import javax.validation.constraints.NotBlank;

public class PasswordChangeFormDTO {
    @NotBlank(message = "{validationOldPasswordEmpty}")
    private String oldPassword;

    @NotBlank(message = "{validationNewPasswordEmpty}")
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}