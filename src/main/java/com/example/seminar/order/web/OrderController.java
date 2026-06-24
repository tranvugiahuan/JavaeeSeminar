package com.example.seminar.order.web;

import com.example.seminar.order.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping({"/", "/orders"})
    public String orders(Model model) {
        if (!model.containsAttribute("orderForm")) {
            model.addAttribute("orderForm", new OrderForm());
        }
        model.addAttribute("products", orderService.findAllProducts());
        model.addAttribute("orders", orderService.findAllOrders());
        return "orders";
    }

    @PostMapping("/orders")
    public String createOrder(@ModelAttribute OrderForm orderForm, RedirectAttributes redirectAttributes) {
        try {
            orderService.createOrder(orderForm);
            redirectAttributes.addFlashAttribute("successMessage", "Thanh toán và lưu đơn hàng thành công.");
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            redirectAttributes.addFlashAttribute("orderForm", orderForm);
        }
        return "redirect:/orders";
    }
}
