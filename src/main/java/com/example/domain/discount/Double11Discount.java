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
        ProductSummary productSummary = groupProductsByName(order);
        int totalDiscountAmount = calculateTotalDiscount(productSummary);
        order.setDiscount(order.getDiscount() + totalDiscountAmount);
    }

    private ProductSummary groupProductsByName(Order order) {
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
        
        return new ProductSummary(productQuantities, productUnitPrices);
    }

    private int calculateTotalDiscount(ProductSummary productSummary) {
        int totalDiscountAmount = 0;
        
        for (Map.Entry<String, Integer> entry : productSummary.quantities.entrySet()) {
            String productName = entry.getKey();
            int totalQuantity = entry.getValue();
            int unitPrice = productSummary.unitPrices.get(productName);
            
            int discountAmount = calculateBulkDiscount(totalQuantity, unitPrice);
            totalDiscountAmount += discountAmount;
        }
        
        return totalDiscountAmount;
    }

    private int calculateBulkDiscount(int totalQuantity, int unitPrice) {
        int bulkSets = totalQuantity / BULK_QUANTITY;
        int discountQuantity = bulkSets * BULK_QUANTITY;
        return (int) (discountQuantity * unitPrice * DISCOUNT_RATE);
    }

    private static class ProductSummary {
        final Map<String, Integer> quantities;
        final Map<String, Integer> unitPrices;

        ProductSummary(Map<String, Integer> quantities, Map<String, Integer> unitPrices) {
            this.quantities = quantities;
            this.unitPrices = unitPrices;
        }
    }
}