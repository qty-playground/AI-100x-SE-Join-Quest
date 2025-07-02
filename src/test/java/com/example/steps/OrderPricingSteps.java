package com.example.steps;

import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.OrderService;
import com.example.domain.Product;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderPricingSteps {

    private OrderService orderService;
    private List<OrderItem> orderItems;
    private Order order;

    @Given("no promotions are applied")
    public void no_promotions_are_applied() {
        orderService = new OrderService();
        orderItems = new ArrayList<>();
    }

    @When("a customer places an order with:")
    public void a_customer_places_an_order_with(io.cucumber.datatable.DataTable dataTable) {
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
}