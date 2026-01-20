package com.example.ecommerce.webhook;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ecommerce.dto.PaymentWebhookRequest;
import com.example.ecommerce.service.PaymentService;

@RestController
@RequestMapping("/api/webhooks")
public class PaymentWebhookController {

    @Autowired
    private PaymentService paymentService;

    // razorpay sends webhook here
    @PostMapping("/payment")
    public ResponseEntity<?> handleWebhook(@RequestBody PaymentWebhookRequest req) {
        paymentService.handleWebhook(req);
        return ResponseEntity.ok(Map.of("status", "ok"));
    }
}
