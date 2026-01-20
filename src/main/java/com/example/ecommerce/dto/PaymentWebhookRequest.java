package com.example.ecommerce.dto;

// webhook payload from razorpay
public class PaymentWebhookRequest {

    private String event;
    private Payload payload;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    // nested classes to match razorpay structure
    public static class Payload {
        private PaymentEntity payment;

        public PaymentEntity getPayment() {
            return payment;
        }

        public void setPayment(PaymentEntity payment) {
            this.payment = payment;
        }
    }

    public static class PaymentEntity {
        private String id;
        private String order_id; // razorpay uses snake_case
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
