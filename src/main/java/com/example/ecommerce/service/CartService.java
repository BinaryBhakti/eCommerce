package com.example.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.dto.AddToCartRequest;
import com.example.ecommerce.dto.CartItemResponse;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.ProductRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private ProductRepository productRepo;

    /**
     * Add item to cart. If already exists, increase quantity
     */
    public CartItem addToCart(AddToCartRequest req) {
        // check if already in cart
        Optional<CartItem> existing = cartRepo.findByUserIdAndProductId(
                req.getUserId(), req.getProductId());

        if (existing.isPresent()) {
            CartItem item = existing.get();
            item.setQuantity(item.getQuantity() + req.getQuantity());
            return cartRepo.save(item);
        }

        // create new
        CartItem newItem = new CartItem(
                req.getUserId(),
                req.getProductId(),
                req.getQuantity());
        return cartRepo.save(newItem);
    }

    public List<CartItemResponse> getCartByUserId(String userId) {
        List<CartItem> items = cartRepo.findByUserId(userId);
        List<CartItemResponse> result = new ArrayList<>();

        for (CartItem item : items) {
            CartItemResponse resp = new CartItemResponse();
            resp.setId(item.getId());
            resp.setProductId(item.getProductId());
            resp.setQuantity(item.getQuantity());

            // fetch product info
            Optional<Product> prod = productRepo.findById(item.getProductId());
            if (prod.isPresent()) {
                Product p = prod.get();
                CartItemResponse.ProductInfo info = new CartItemResponse.ProductInfo(
                        p.getId(), p.getName(), p.getPrice());
                resp.setProduct(info);
            }
            result.add(resp);
        }
        return result;
    }

    public List<CartItem> getCartItemsByUserId(String userId) {
        return cartRepo.findByUserId(userId);
    }

    public void clearCart(String userId) {
        cartRepo.deleteByUserId(userId);
    }
}
