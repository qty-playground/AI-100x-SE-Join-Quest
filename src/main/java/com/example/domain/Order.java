package com.example.domain;

import java.util.List;

public class Order {
    private List<OrderItem> orderItems;
    private int totalAmount;
    private int originalAmount;
    private int discount;

    public Order(List<OrderItem> orderItems, int totalAmount, int originalAmount, int discount) {
        this.orderItems = orderItems;
        this.totalAmount = totalAmount;
        this.originalAmount = originalAmount;
        this.discount = discount;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public int getOriginalAmount() {
        return originalAmount;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public void applyDiscount() {
        this.totalAmount = this.originalAmount - this.discount;
    }
}
