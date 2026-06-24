package com.example.seminar;

import com.example.seminar.order.domain.Product;
import com.example.seminar.order.repository.OrderRepository;
import com.example.seminar.order.repository.ProductRepository;
import com.example.seminar.order.service.OrderService;
import com.example.seminar.order.web.OrderDetailForm;
import com.example.seminar.order.web.OrderForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class SeminarApplicationTests {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Autowired
    SeminarApplicationTests(OrderService orderService,
                            OrderRepository orderRepository,
                            ProductRepository productRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Test
    void contextLoads() {
    }

    @Test
    void createOrderRollsBackWhenQuantityExceedsStock() {
        orderRepository.deleteAll();
        Product product = productRepository.findAll().get(0);

        orderService.createOrder(orderForm(product, 1));
        Product afterSuccessfulOrder = productRepository.findById(product.getId()).orElseThrow();

        assertThat(orderRepository.count()).isEqualTo(1);
        assertThat(afterSuccessfulOrder.getStockQuantity()).isEqualTo(product.getStockQuantity() - 1);

        int stockBeforeFailedOrder = afterSuccessfulOrder.getStockQuantity();
        assertThatThrownBy(() -> orderService.createOrder(orderForm(afterSuccessfulOrder, stockBeforeFailedOrder + 1)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("rollback");

        Product afterFailedOrder = productRepository.findById(product.getId()).orElseThrow();
        assertThat(orderRepository.count()).isEqualTo(1);
        assertThat(afterFailedOrder.getStockQuantity()).isEqualTo(stockBeforeFailedOrder);
    }

    private OrderForm orderForm(Product product, int quantity) {
        OrderForm form = new OrderForm();
        form.setCustomerName("Nguyen Van A");

        OrderDetailForm detail = new OrderDetailForm();
        detail.setProductId(product.getId());
        detail.setProductName(product.getName());
        detail.setQuantity(quantity);
        detail.setPrice(product.getPrice());
        form.getDetails().add(detail);

        return form;
    }
}
