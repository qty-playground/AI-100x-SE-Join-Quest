package com.example.domain.discount;

import com.example.domain.Order;

public class ThresholdDiscount implements DiscountStrategy {
    private final int threshold;
    private final int discount;

    public ThresholdDiscount(int threshold, int discount) {
        this.threshold = threshold;
        this.discount = discount;
    }

    @Override
    public void applyDiscount(Order order) {
        if (order.getOriginalAmount() >= threshold) {
            order.setDiscount(order.getDiscount() + discount);
        }
    }
}
