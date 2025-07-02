package com.example.domain.discount;

import com.example.domain.Order;
import com.example.domain.OrderItem;

public class BuyOneGetOneDiscount implements DiscountStrategy {
    @Override
    public void applyDiscount(Order order) {
        for (OrderItem item : order.getOrderItems()) {
            if ("cosmetics".equals(item.getProduct().getCategory())) {
                item.setQuantity(item.getQuantity() + 1);
            }
        }
    }
}
