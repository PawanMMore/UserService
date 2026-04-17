package com.pawan.userservice.dtos;

public class LoginRequestDto {
    private String userEmail;
    private String passward;

    public String getUserEmail() {
        return userEmail;
    }

    public String getPassward() {
        return passward;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setPassward(String passward) {
        this.passward = passward;
    }
}
