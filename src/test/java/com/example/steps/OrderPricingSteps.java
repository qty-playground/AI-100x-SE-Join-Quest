package com.example.steps;

import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.OrderService;
import com.example.domain.Product;
import com.example.domain.discount.DiscountStrategy;
import com.example.domain.discount.ThresholdDiscount;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderPricingSteps {

    private OrderService orderService;
    private List<OrderItem> orderItems;
    private Order order;
    private List<DiscountStrategy> discountStrategies;

    @Before
    public void setUp() {
        discountStrategies = new ArrayList<>();
    }

    @Given("no promotions are applied")
    public void no_promotions_are_applied() {
        orderItems = new ArrayList<>();
    }

    @When("a customer places an order with:")
    public void a_customer_places_an_order_with(io.cucumber.datatable.DataTable dataTable) {
        orderService = new OrderService(discountStrategies);
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> columns : rows) {
            Product product = new Product(
                    columns.get("productName"),
                    Integer.parseInt(columns.get("unitPrice")),
                    columns.get("category")
            );
            orderItems.add(new OrderItem(product, Integer.parseInt(columns.get("quantity"))));
        }

        order = orderService.checkout(orderItems);
    }

    @Then("the order summary should be:")
    public void the_order_summary_should_be(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> summary = dataTable.asMaps().get(0);
        assertEquals(Integer.parseInt(summary.get("totalAmount")), order.getTotalAmount());
        if (summary.containsKey("originalAmount")) {
            assertEquals(Integer.parseInt(summary.get("originalAmount")), order.getOriginalAmount());
        }
        if (summary.containsKey("discount")) {
            assertEquals(Integer.parseInt(summary.get("discount")), order.getDiscount());
        }
    }

    @And("the customer should receive:")
    public void the_customer_should_receive(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> expectedItems = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> expectedItem : expectedItems) {
            boolean found = order.getOrderItems().stream().anyMatch(actualItem ->
                    actualItem.getProduct().getName().equals(expectedItem.get("productName")) &&
                            actualItem.getQuantity() == Integer.parseInt(expectedItem.get("quantity"))
            );
            assertEquals(true, found);
        }
    }

    @Given("the threshold discount promotion is configured:")
    public void the_threshold_discount_promotion_is_configured(io.cucumber.datatable.DataTable dataTable) {
        orderItems = new ArrayList<>();
        Map<String, String> config = dataTable.asMaps().get(0);
        discountStrategies.add(new ThresholdDiscount(
                Integer.parseInt(config.get("threshold")),
                Integer.parseInt(config.get("discount"))
        ));
    }

    @Given("the buy one get one promotion for cosmetics is active")
    public void the_buy_one_get_one_promotion_for_cosmetics_is_active() {
        orderItems = new ArrayList<>();
        discountStrategies.add(new com.example.domain.discount.BuyOneGetOneDiscount());
    }

    @Given("the Double {int} bulk discount promotion is active")
    public void the_double_bulk_discount_promotion_is_active(Integer int1) {
        orderItems = new ArrayList<>();
        // TODO: Add Double11Discount implementation
        throw new io.cucumber.java.PendingException();
    }
}
