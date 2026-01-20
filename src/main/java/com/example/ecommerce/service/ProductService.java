package com.example.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepo;

    // create new product
    public Product createProduct(Product product) {
        return productRepo.save(product);
    }

    // get all
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public Optional<Product> getProductById(String id) {
        return productRepo.findById(id);
    }

    public Product updateProduct(String id, Product product) {
        product.setId(id);
        return productRepo.save(product);
    }

    public void deleteProduct(String id) {
        productRepo.deleteById(id);
    }

    // reduce stock when order placed
    public boolean reduceStock(String productId, int qty) {
        Optional<Product> opt = productRepo.findById(productId);
        if (opt.isEmpty())
            return false;

        Product p = opt.get();
        if (p.getStock() < qty)
            return false;

        p.setStock(p.getStock() - qty);
        productRepo.save(p);
        return true;
    }
}
