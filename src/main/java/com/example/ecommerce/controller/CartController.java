package com.example.ecommerce.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ecommerce.dto.AddToCartRequest;
import com.example.ecommerce.dto.CartItemResponse;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<CartItem> addItem(@RequestBody AddToCartRequest req) {
        CartItem item = cartService.addToCart(req);
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public List<CartItemResponse> getCart(@PathVariable String userId) {
        return cartService.getCartByUserId(userId);
    }

    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<?> clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);

        Map<String, String> resp = new HashMap<>();
        resp.put("message", "Cart cleared successfully");
        return ResponseEntity.ok(resp);
    }
}
