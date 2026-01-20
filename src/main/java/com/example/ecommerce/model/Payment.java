package com.example.ecommerce.model;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "payments")
public class Payment {

    @Id
    private String id;
    private String orderId;
    private double amount;
    private String status; // PENDING, SUCCESS, FAILED
    private String paymentId; // razorpay payment id
    private String razorpayOrderId;
    private LocalDateTime createdAt;

    public Payment() {
        this.createdAt = LocalDateTime.now();
        this.status = "PENDING";
    }

    // getters
    public String getId() {
        return id;
    }

    public String getOrderId() {
        return orderId;
    }

    public double getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getRazorpayOrderId() {
        return razorpayOrderId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // setters
    public void setId(String id) {
        this.id = id;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public void setRazorpayOrderId(String razorpayOrderId) {
        this.razorpayOrderId = razorpayOrderId;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
