package com.victortello.ws.webservice.model.request;

public class PasswordResetModel {
    private String password;
    private String token;

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
