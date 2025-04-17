package org.AP;

import java.util.UUID;

public abstract class Account {
    private final String id;
    private String username;
    private String password; // Should be hashed in real implementation
    private String email;

    public Account(String username, String password, String email) {
        this.id = UUID.randomUUID().toString();
        this.username = username;
        this.password = password;
        this.email = email;
    }



    // Getters and Setters
    public String getId() { return id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }
}