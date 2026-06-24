package com.example.seminar.order.config;

import com.example.seminar.order.model.Product;
import com.example.seminar.order.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class ProductDataSeeder implements CommandLineRunner {

    private final ProductRepository productRepository;

    public ProductDataSeeder(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {
        if (productRepository.count() > 0) {
            return;
        }

        productRepository.saveAll(List.of(
                new Product("Laptop Dell Inspiron", new BigDecimal("15500000"), 5),
                new Product("Bàn phím cơ", new BigDecimal("850000"), 12),
                new Product("Chuột Logitech", new BigDecimal("420000"), 20),
                new Product("Màn hình 24 inch", new BigDecimal("3200000"), 8),
                new Product("Tai nghe Bluetooth", new BigDecimal("690000"), 15),
                new Product("USB 64GB", new BigDecimal("180000"), 30)
        ));
    }
}
