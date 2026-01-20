package com.example.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.dto.CreateOrderRequest;
import com.example.ecommerce.dto.OrderResponse;
import com.example.ecommerce.model.*;
import com.example.ecommerce.repository.*;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private OrderItemRepository orderItemRepo;
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private PaymentRepository paymentRepo;

    public OrderResponse createOrder(CreateOrderRequest req) {
        String userId = req.getUserId();
        List<CartItem> cartItems = cartService.getCartItemsByUserId(userId);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty!");
        }

        // calc total
        double total = 0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem ci : cartItems) {
            Optional<Product> prodOpt = productRepo.findById(ci.getProductId());
            if (prodOpt.isPresent()) {
                Product p = prodOpt.get();
                double itemTotal = p.getPrice() * ci.getQuantity();
                total += itemTotal;

                OrderItem oi = new OrderItem();
                oi.setProductId(ci.getProductId());
                oi.setQuantity(ci.getQuantity());
                oi.setPrice(p.getPrice());
                orderItems.add(oi);
            }
        }

        // save order
        Order order = new Order(userId, total);
        order = orderRepo.save(order);

        // save order items
        List<OrderResponse.OrderItemInfo> itemInfoList = new ArrayList<>();
        for (OrderItem oi : orderItems) {
            oi.setOrderId(order.getId());
            orderItemRepo.save(oi);

            itemInfoList.add(new OrderResponse.OrderItemInfo(
                    oi.getProductId(),
                    oi.getQuantity(),
                    oi.getPrice()));
        }

        // clear cart after creating order
        cartService.clearCart(userId);

        // build response
        OrderResponse resp = new OrderResponse();
        resp.setId(order.getId());
        resp.setUserId(order.getUserId());
        resp.setTotalAmount(order.getTotalAmount());
        resp.setStatus(order.getStatus());
        resp.setItems(itemInfoList);

        return resp;
    }

    public OrderResponse getOrderById(String orderId) {
        Optional<Order> orderOpt = orderRepo.findById(orderId);
        if (orderOpt.isEmpty()) {
            throw new RuntimeException("Order not found: " + orderId);
        }

        Order order = orderOpt.get();
        List<OrderItem> items = orderItemRepo.findByOrderId(orderId);

        OrderResponse resp = new OrderResponse();
        resp.setId(order.getId());
        resp.setUserId(order.getUserId());
        resp.setTotalAmount(order.getTotalAmount());
        resp.setStatus(order.getStatus());

        // check if payment exists
        Optional<Payment> payOpt = paymentRepo.findByOrderId(orderId);
        if (payOpt.isPresent()) {
            Payment pay = payOpt.get();
            resp.setPayment(new OrderResponse.PaymentInfo(
                    pay.getId(), pay.getStatus(), pay.getAmount()));
        }

        // add items
        List<OrderResponse.OrderItemInfo> itemInfoList = new ArrayList<>();
        for (OrderItem item : items) {
            itemInfoList.add(new OrderResponse.OrderItemInfo(
                    item.getProductId(), item.getQuantity(), item.getPrice()));
        }
        resp.setItems(itemInfoList);

        return resp;
    }

    public Order updateOrderStatus(String orderId, String newStatus) {
        Optional<Order> opt = orderRepo.findById(orderId);
        if (opt.isEmpty())
            throw new RuntimeException("Order not found");

        Order order = opt.get();
        order.setStatus(newStatus);
        return orderRepo.save(order);
    }
}
