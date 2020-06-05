package com.example.alvirahman.version02;

public class User {

    public String title;
    public String description;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String title, String description) {
        this.title = title;
        this.description = description;
    }

}