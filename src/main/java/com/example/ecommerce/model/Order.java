package com.example.ecommerce.model;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orders")
public class Order {

    @Id
    private String id;
    private String userId;
    private double totalAmount;
    private String status; // CREATED, PAID, PAYMENT_FAILED etc
    private LocalDateTime createdAt;

    public Order() {
        this.createdAt = LocalDateTime.now();
        this.status = "CREATED";
    }

    public Order(String userId, double total) {
        this();
        this.userId = userId;
        this.totalAmount = total;
    }

    // getters
    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // setters
    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
