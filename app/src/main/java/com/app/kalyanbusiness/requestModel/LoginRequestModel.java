package com.app.kalyanbusiness.requestModel;

import java.io.Serializable;

public class LoginRequestModel implements Serializable {
    String userid;
    String password;

    public LoginRequestModel(String phoneNumber, String password) {
        this.userid = phoneNumber;
        this.password = password;
    }

    public LoginRequestModel() {
    }

    public String getPhoneNumber() {
        return userid;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.userid = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
