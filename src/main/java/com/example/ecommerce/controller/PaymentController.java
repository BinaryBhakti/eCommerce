package com.example.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ecommerce.dto.PaymentRequest;
import com.example.ecommerce.dto.PaymentResponse;
import com.example.ecommerce.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody PaymentRequest req) {
        PaymentResponse resp = paymentService.createPayment(req);
        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }
}
