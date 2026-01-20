package com.example.ecommerce.dto;

/**
 * Cart item with product details for response
 */
public class CartItemResponse {
    private String id;
    private String productId;
    private int quantity;
    private ProductInfo product;

    public CartItemResponse() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ProductInfo getProduct() {
        return product;
    }

    public void setProduct(ProductInfo product) {
        this.product = product;
    }

    // product info
    public static class ProductInfo {
        private String id;
        private String name;
        private double price;

        public ProductInfo() {
        }

        public ProductInfo(String id, String name, double price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }
}
