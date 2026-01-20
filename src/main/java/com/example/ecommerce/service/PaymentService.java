package com.example.ecommerce.service;

import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.dto.PaymentRequest;
import com.example.ecommerce.dto.PaymentResponse;
import com.example.ecommerce.dto.PaymentWebhookRequest;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.Payment;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.PaymentRepository;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepo;

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private RazorpayClient razorpayClient;

    /**
     * Create a razorpay order and store payment record
     */
    public PaymentResponse createPayment(PaymentRequest req) {
        try {
            // razorpay wants amount in paise
            int amountInPaise = (int) (req.getAmount() * 100);

            JSONObject orderReq = new JSONObject();
            orderReq.put("amount", amountInPaise);
            orderReq.put("currency", "INR");
            orderReq.put("receipt", req.getOrderId());

            com.razorpay.Order rpOrder = razorpayClient.orders.create(orderReq);
            String rpOrderId = rpOrder.get("id");

            // save to db
            Payment payment = new Payment();
            payment.setOrderId(req.getOrderId());
            payment.setAmount(req.getAmount());
            payment.setRazorpayOrderId(rpOrderId);
            // status already set to PENDING in constructor

            payment = paymentRepo.save(payment);

            return new PaymentResponse(
                    payment.getId(),
                    payment.getOrderId(),
                    payment.getAmount(),
                    payment.getStatus(),
                    rpOrderId);

        } catch (RazorpayException e) {
            throw new RuntimeException("Razorpay error: " + e.getMessage());
        }
    }

    /**
     * Handle webhook from razorpay
     */
    public void handleWebhook(PaymentWebhookRequest webhook) {
        String event = webhook.getEvent();

        if ("payment.captured".equals(event)) {
            handlePaymentCaptured(webhook);
        } else if ("payment.failed".equals(event)) {
            handlePaymentFailed(webhook);
        }
        // ignore other events
    }

    private void handlePaymentCaptured(PaymentWebhookRequest webhook) {
        var paymentEntity = webhook.getPayload().getPayment();
        String rpPaymentId = paymentEntity.getId();
        String rpOrderId = paymentEntity.getOrder_id();

        Optional<Payment> opt = paymentRepo.findByRazorpayOrderId(rpOrderId);
        if (opt.isEmpty())
            return; // not found, ignore

        Payment payment = opt.get();
        payment.setPaymentId(rpPaymentId);
        payment.setStatus("SUCCESS");
        paymentRepo.save(payment);

        // update order status
        Optional<Order> orderOpt = orderRepo.findById(payment.getOrderId());
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setStatus("PAID");
            orderRepo.save(order);
        }
    }

    private void handlePaymentFailed(PaymentWebhookRequest webhook) {
        var paymentEntity = webhook.getPayload().getPayment();
        String rpOrderId = paymentEntity.getOrder_id();

        Optional<Payment> opt = paymentRepo.findByRazorpayOrderId(rpOrderId);
        if (opt.isEmpty())
            return;

        Payment payment = opt.get();
        payment.setStatus("FAILED");
        paymentRepo.save(payment);

        // update order
        Optional<Order> orderOpt = orderRepo.findById(payment.getOrderId());
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setStatus("PAYMENT_FAILED");
            orderRepo.save(order);
        }
    }

    public Optional<Payment> getPaymentByOrderId(String orderId) {
        return paymentRepo.findByOrderId(orderId);
    }
}
