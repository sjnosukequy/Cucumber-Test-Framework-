Feature: Verify Cart Functionality
  As a tester
  I want to validate the cart functionality on the UI
  So that cart states, item details, deletion behavior, and checkout flow work correctly
  @TC_UI_Cart_Empty_001 @ui @cart @high
  Scenario: Verify user sees empty cart message and no cart table when cart has no items
    Given I am at the homepage for cart testing
    When I open the Cart page from the header
    Then the Cart page should open
    And the cart product table should not be displayed
    And the checkout button should be hidden
    And an empty cart message with a continue shopping prompt should be displayed
  @TC_UI_Cart_Empty_002 @ui @cart @high
  Scenario: Verify user can use empty cart prompt to continue shopping
    Given I am at the homepage for cart testing
    When I open the Cart page from the header
    Then the Cart page should open
    And an empty cart message with a continue shopping prompt should be displayed
    When I click the continue shopping prompt
    Then I should be navigated to the product page

  @TC_UI_Cart_View_003 @ui @cart @high
  Scenario Outline: Verify user sees required product details for a single cart item
    Given I am at the homepage for cart testing
    Then I have added "<product>" to the cart
    When I open the Cart page from the header
    Then the cart product table should be displayed
    And the cart row for "<product>" should be displayed
    And the product image, name, price, and quantity should be visible and correct
    And the line total should be displayed correctly as price multiplied by quantity
    And the delete icon should be displayed for the item
    And the Proceed To Checkout button should be visible

    Examples:
      | product  |
      | Blue Top |

  @TC_UI_Cart_View_004 @ui @cart @high
  Scenario Outline: Verify user sees all required details for multiple cart items
    Given I am at the homepage for cart testing
    Then I have added "<product>" to the cart
    When I open the Cart page from the header
    Then the cart product table should be displayed
    And the cart row for "<product>" should be displayed
    And the product image, name, price, and quantity should be visible and correct
    And the line total should be displayed correctly as price multiplied by quantity
    And the delete icon should be displayed for the item
    And the Proceed To Checkout button should be visible

    Examples:
      | product             |
      | Blue Top;Men Tshirt |

  @TC_UI_Cart_Delete_005 @ui @cart @high
  Scenario Outline: Verify user can remove a selected item from cart with multiple items
    Given I am at the homepage for cart testing
    Then I have added "<product>" to the cart
    When I open the Cart page from the header
    Then the cart product table should be displayed
    When I delete the product "<deletedProduct>" from the cart
    Then the deleted items should be removed
    And the non-deleted items should remain displayed correctly
    And the line total should be displayed correctly as price multiplied by quantity

    Examples:
      | product             | deletedProduct |
      | Blue Top;Men Tshirt | Blue Top       |
