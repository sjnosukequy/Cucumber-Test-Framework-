Feature: Verify Checkout Functionality
  As a tester
  I want to validate the checkout functionality on the UI
  So that only eligible users can access checkout, review order details, and complete payment correctly

  @TC_UI_Checkout_Checkout_001 @ui @checkout @high
  Scenario: Verify user is redirected to Signup Login page when not authenticated
    Given I am at the homepage for checkout testing
    And I am not logged in on the UI
    When I navigate directly to "/checkout"
    Then I should be redirected to "/login"

  @TC_UI_Checkout_Checkout_002 @ui @checkout @high
  Scenario: Verify logged in user without cart items cannot access checkout page directly
    Given I am at the homepage for checkout testing
    And I am logged in on the UI 
    And verify I have no items in the cart
    When I navigate directly to "/checkout"
    Then I should be redirected to "/products"
