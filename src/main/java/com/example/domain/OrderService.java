package com.example.domain;

import com.example.domain.discount.DiscountStrategy;

import java.util.List;

public class OrderService {
    private List<DiscountStrategy> discountStrategies;

    public OrderService(List<DiscountStrategy> discountStrategies) {
        this.discountStrategies = discountStrategies;
    }

    public Order checkout(List<OrderItem> items) {
        int originalAmount = items.stream().mapToInt(OrderItem::getSubtotal).sum();
        Order order = new Order(items, originalAmount, originalAmount, 0);

        for (DiscountStrategy strategy : discountStrategies) {
            strategy.applyDiscount(order);
        }

        order.applyDiscount();
        return order;
    }
}