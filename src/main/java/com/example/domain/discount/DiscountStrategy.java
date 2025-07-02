package com.example.domain.discount;

import com.example.domain.Order;

public interface DiscountStrategy {
    void applyDiscount(Order order);
}
