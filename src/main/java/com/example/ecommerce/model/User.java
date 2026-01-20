package com.example.ecommerce.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * User entity for storing customer info
 */
@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String username;
    private String email;

    // default constructor needed by mongo
    public User() {
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    // getters & setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
