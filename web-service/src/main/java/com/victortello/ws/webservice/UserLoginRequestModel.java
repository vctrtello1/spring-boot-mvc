package com.victortello.ws.webservice;

public class UserLoginRequestModel {

    private String email;
    private String password;

    public UserLoginRequestModel() {
    }

    public UserLoginRequestModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }    
}
