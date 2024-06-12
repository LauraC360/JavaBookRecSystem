package com.example.project.authentication.user;

import org.springframework.stereotype.Service;

@Service
public class UserSession {

    private static UserSession instance;
    private String username;
    private String encodedPassword;

    private UserSession() {
    }

    public static synchronized UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void setUser(String username, String encodedPassword) {
        this.username = username;
        this.encodedPassword = encodedPassword;
    }

    public String getUsername() {
        return username;
    }

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public void clear() {
        this.username = null;
        this.encodedPassword = null;
    }
}