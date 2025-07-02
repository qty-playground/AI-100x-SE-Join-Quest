package com.example.domain.discount;

import com.example.domain.Order;
import com.example.domain.OrderItem;

import java.util.Map;
import java.util.HashMap;

public class Double11Discount implements DiscountStrategy {
    private static final int BULK_QUANTITY = 10;
    private static final double DISCOUNT_RATE = 0.2;

    @Override
    public void applyDiscount(Order order) {
        int totalDiscountAmount = 0;
        
        // Group items by product name to apply bulk discount
        Map<String, Integer> productQuantities = new HashMap<>();
        Map<String, Integer> productUnitPrices = new HashMap<>();
        
        for (OrderItem item : order.getOrderItems()) {
            String productName = item.getProduct().getName();
            int quantity = item.getQuantity();
            int unitPrice = item.getProduct().getUnitPrice();
            
            productQuantities.put(productName, 
                productQuantities.getOrDefault(productName, 0) + quantity);
            productUnitPrices.put(productName, unitPrice);
        }
        
        // Calculate discount for each product
        for (Map.Entry<String, Integer> entry : productQuantities.entrySet()) {
            String productName = entry.getKey();
            int totalQuantity = entry.getValue();
            int unitPrice = productUnitPrices.get(productName);
            
            // Calculate how many bulk sets (sets of 10) qualify for discount
            int bulkSets = totalQuantity / BULK_QUANTITY;
            int discountQuantity = bulkSets * BULK_QUANTITY;
            
            // Calculate discount amount for this product
            int discountAmount = (int) (discountQuantity * unitPrice * DISCOUNT_RATE);
            totalDiscountAmount += discountAmount;
        }
        
        order.setDiscount(order.getDiscount() + totalDiscountAmount);
    }
}