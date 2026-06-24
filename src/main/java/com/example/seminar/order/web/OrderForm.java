package com.example.seminar.order.web;

import java.util.ArrayList;
import java.util.List;

public class OrderForm {

    private String customerName;
    private List<OrderDetailForm> details = new ArrayList<>();

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<OrderDetailForm> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetailForm> details) {
        this.details = details;
    }
}
