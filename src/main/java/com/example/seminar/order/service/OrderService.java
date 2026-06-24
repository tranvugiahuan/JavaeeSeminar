package com.example.seminar.order.service;

import com.example.seminar.order.model.Order;
import com.example.seminar.order.model.OrderDetail;
import com.example.seminar.order.model.Product;
import com.example.seminar.order.repository.OrderRepository;
import com.example.seminar.order.repository.ProductRepository;
import com.example.seminar.order.controller.OrderDetailForm;
import com.example.seminar.order.controller.OrderForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<Order> findAllOrders() {
        return orderRepository.findAllByOrderByOrderDateDesc();
    }

    @Transactional(readOnly = true)
    public List<Product> findAllProducts() {
        return productRepository.findAllByOrderByNameAsc();
    }

    @Transactional
    public Order createOrder(OrderForm form) {
        if (form == null || isBlank(form.getCustomerName())) {
            throw new IllegalArgumentException("Vui lòng nhập tên khách hàng.");
        }

        List<OrderDetailForm> details = form.getDetails() == null ? Collections.emptyList() : form.getDetails();
        List<OrderDetailForm> validDetails = details.stream()
                .filter(this::hasProduct)
                .toList();

        if (validDetails.isEmpty()) {
            throw new IllegalArgumentException("Vui lòng chọn ít nhất một sản phẩm.");
        }

        Order order = new Order(form.getCustomerName().trim(), LocalDateTime.now());
        for (OrderDetailForm detailForm : validDetails) {
            validateDetail(detailForm);
            Product product = findProduct(detailForm.getProductId());
            order.addDetail(new OrderDetail(
                    product.getId(),
                    product.getName(),
                    detailForm.getQuantity(),
                    product.getPrice()
            ));
        }

        Order savedOrder = orderRepository.saveAndFlush(order);
        decreaseStockAfterSaving(validDetails);

        return savedOrder;
    }

    private boolean hasProduct(OrderDetailForm detail) {
        return detail != null && detail.getProductId() != null;
    }

    private Product findProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Sản phẩm không tồn tại."));
    }

    private void validateDetail(OrderDetailForm detail) {
        if (detail.getQuantity() == null || detail.getQuantity() <= 0) {
            throw new IllegalArgumentException("Số lượng phải lớn hơn 0.");
        }
    }

    private void decreaseStockAfterSaving(List<OrderDetailForm> details) {
        for (OrderDetailForm detail : details) {
            Product product = findProduct(detail.getProductId());
            product.decreaseStock(detail.getQuantity());
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
