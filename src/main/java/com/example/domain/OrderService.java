package com.example.domain;

import java.util.List;

public class OrderService {
    public Order checkout(List<OrderItem> items) {
        int totalAmount = items.stream().mapToInt(OrderItem::getSubtotal).sum();
        return new Order(items, totalAmount, totalAmount, 0);
    }
}
