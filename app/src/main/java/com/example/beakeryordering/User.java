package com.example.beakeryordering;

public class User {
    private String name;
    private String email;
    private String role;

    // Default constructor required for Firebase
    public User() {
    }

    public User(String name, String email, String role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}
