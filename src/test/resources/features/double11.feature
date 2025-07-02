@double11_discount
Feature: Double 11 Bulk Discount
  As a shopper during Double 11 sale
  I want to get 20% discount for every 10 items of the same product
  So that I can save money when buying in bulk

  Background:
    Given the Double 11 bulk discount promotion is active

  Scenario: Buy 12 items of same product - partial bulk discount
    When a customer places an order with:
      | productName | quantity | unitPrice |
      | 襪子        | 12       | 100       |
    Then the order summary should be:
      | totalAmount |
      | 1000        |
    And the customer should receive:
      | productName | quantity |
      | 襪子        | 12       |

  @ignore
  Scenario: Buy 27 items of same product - multiple bulk discounts
    When a customer places an order with:
      | productName | quantity | unitPrice |
      | 襪子        | 27       | 100       |
    Then the order summary should be:
      | totalAmount |
      | 2300        |
    And the customer should receive:
      | productName | quantity |
      | 襪子        | 27       |

  @ignore
  Scenario: Buy 10 different products - no bulk discount applies
    When a customer places an order with:
      | productName | quantity | unitPrice |
      | 商品A       | 1        | 100       |
      | 商品B       | 1        | 100       |
      | 商品C       | 1        | 100       |
      | 商品D       | 1        | 100       |
      | 商品E       | 1        | 100       |
      | 商品F       | 1        | 100       |
      | 商品G       | 1        | 100       |
      | 商品H       | 1        | 100       |
      | 商品I       | 1        | 100       |
      | 商品J       | 1        | 100       |
    Then the order summary should be:
      | totalAmount |
      | 1000        |
    And the customer should receive:
      | productName | quantity |
      | 商品A       | 1        |
      | 商品B       | 1        |
      | 商品C       | 1        |
      | 商品D       | 1        |
      | 商品E       | 1        |
      | 商品F       | 1        |
      | 商品G       | 1        |
      | 商品H       | 1        |
      | 商品I       | 1        |
      | 商品J       | 1        |