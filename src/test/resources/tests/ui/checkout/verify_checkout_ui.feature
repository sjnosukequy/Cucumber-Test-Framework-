Feature: Verify Checkout UI
  As a tester
  I want to validate the checkout functionality on the UI
  So that only eligible users can access checkout, review order details, and complete payment correctly

  @TC_UI_Checkout_Checkout_001 @ui @checkout @high
  Scenario: Verify guest user is redirected to Signup Login page when accessing checkout page directly
    Given I am on the home page
    And I am not logged in on the UI
    When I navigate directly to "/checkout"
    Then I should be redirected to "/login"

  @TC_UI_Checkout_Checkout_002 @ui @checkout @high
  Scenario: Verify logged in user without cart items cannot access checkout page directly
    Given I am on the home page
    And I am logged in on the UI
    And verify I have no items in the cart
    When I navigate directly to "/checkout"
    Then I should be redirected to "/products"

  @TC_UI_Checkout_Checkout_003 @ui @checkout @high
  Scenario Outline: Verify logged in user with cart items can access checkout page
    Given I am on the home page
    And I am logged in on the UI
    And I have added "<product>" to the cart
    When I navigate directly to "/checkout"
    Then the checkout page should load successfully
    And the Delivery Address section should be visible
    And the Billing Address section should be visible
    And the comment area should be visible
    And the order summary should be visible
    And the Place Order button should be visible

    Examples:
      | product  |
      | Blue Top |

  @TC_UI_Checkout_Checkout_004 @ui @checkout @high
  Scenario Outline: Verify billing address is auto populated from user profile data
    Given I am on the home page
    And I am logged in on the UI
    And I have added "<product>" to the cart
    When I navigate directly to "/checkout"
    Then the checkout page should load successfully
    And the Delivery Address section should the correct display profile data
    And the Billing Address section should the correct display profile data

    Examples:
      | product  |
      | Blue Top |

  @TC_UI_Checkout_Checkout_005 @ui @checkout @high
  Scenario Outline: Verify order comment input field is displayed on checkout page
    Given I am on the home page
    And I am logged in on the UI
    And I have added "<product>" to the cart
    When I navigate directly to "/checkout"
    Then the checkout page should load successfully
    And the comment area should be visible
    When I enter "<comment>" into the order comment field it should appear correctly in the text box

    Examples:
      | product  | comment                 |
      | Blue Top | Leave at reception desk |

  @TC_UI_Checkout_Checkout_006 @ui @checkout @high
  Scenario Outline: Verify order summary displays single cart item details correctly
    Given I am on the home page
    And I am logged in on the UI
    And I have added "<product>" to the cart
    When I navigate directly to "/checkout"
    Then the checkout page should load successfully
    Then the cart product table should be displayed - "checkout"
    And the product image, name, price, and quantity should be visible and correct - "checkout"
    And the line total should be displayed correctly as price multiplied by quantity - "checkout"

    Examples:
      | product  |
      | Blue Top |

  @TC_UI_Checkout_Checkout_007 @ui @checkout @high
  Scenario Outline: Verify order summary displays multiple cart item details correctly
    Given I am on the home page
    And I am logged in on the UI
    And I have added "<product>" to the cart
    When I navigate directly to "/checkout"
    Then the checkout page should load successfully
    Then the cart product table should be displayed - "checkout"
    And the product image, name, price, and quantity should be visible and correct - "checkout"
    And the line total should be displayed correctly as price multiplied by quantity - "checkout"

    Examples:
      | product             |
      | Blue Top;Men Tshirt |

  @TC_UI_Checkout_Checkout_008 @ui @checkout @high
  Scenario Outline: Verify overall order total is displayed clearly at bottom of order summary
    Given I am on the home page
    And I am logged in on the UI
    And I have added "<product>" to the cart
    When I navigate directly to "/checkout"
    Then the checkout page should load successfully
    Then the cart product table should be displayed - "checkout"
    And the overall order total should be correctly displayed - checkout

    Examples:
      | product             |
      | Blue Top;Men Tshirt |

  @TC_UI_Checkout_Checkout_009 @ui @checkout @high
  Scenario Outline: Verify user is navigated to payment details page after clicking Place Order
    Given I am on the home page
    And I am logged in on the UI
    And I have added "<product>" to the cart
    When I navigate directly to "/checkout"
    And I click the Place Order button
    Then I should be redirected to "/payment"

    Examples:
      | product  |
      | Blue Top |

  @TC_UI_Checkout_Checkout_010 @ui @checkout @high
  Scenario Outline: Verify user can input checkout details and proceed to payment confirmation
    Given I am on the home page
    And I am logged in on the UI
    And I have added "<product>" to the cart
    When I navigate directly to "/checkout"
    And I enter "<comment>" into the order comment field it should appear correctly in the text box
    And I click the Place Order button
    Then I should be redirected to "/payment"
    When I enter "<nameOnCard>" into the Name on Card field
    And I enter "<cardNumber>" into the Card Number field
    And I enter "<cvc>" into the CVC field
    And I enter "<expirationMonth>" into the Expiration Month field
    And I enter "<expirationYear>" into the Expiration Year field
    And I click the Pay and Confirm Order button
    And I should be redirected to the invoice page

    Examples:
      | product  | comment                 | nameOnCard | cardNumber       | cvc | expirationMonth | expirationYear |
      | Blue Top | Leave at reception desk | Faker      | 4655724338490811 | 906 |              11 |           2027 |

  @TC_UI_Checkout_Checkout_011 @ui @checkout @high
  Scenario Outline: Verify user cannot proceed to payment confirmation when required card details are missing
    Given I am on the home page
    And I am logged in on the UI
    And I have added "<product>" to the cart
    When I navigate directly to "/checkout"
    And I click the Place Order button
    Then I should be redirected to "/payment"
    When I enter "<nameOnCard>" into the Name on Card field
    And I enter "<cardNumber>" into the Card Number field
    And I enter "<cvc>" into the CVC field
    And I enter "<expirationMonth>" into the Expiration Month field
    And I enter "<expirationYear>" into the Expiration Year field
    And I click the Pay and Confirm Order button
    Then validation messages should be displayed for required payment fields

    Examples:
      | product  | nameOnCard | cardNumber       | cvc | expirationMonth | expirationYear |
      | Blue Top |            | 4655724338490811 | 906 |              11 |           2027 |
      | Blue Top | Faker      |                  | 906 |              11 |           2027 |
      | Blue Top | Faker      | 4655724338490811 |     |              11 |           2027 |
      | Blue Top | Faker      | 4655724338490811 | 906 |                 |           2027 |
      | Blue Top | Faker      | 4655724338490811 | 906 |              11 |                |

  @TC_UI_Checkout_Checkout_012 @ui @checkout @high
  Scenario Outline: Verify user cannot proceed when expiration month is invalid
    Given I am on the home page
    And I am logged in on the UI
    And I have added "<product>" to the cart
    When I navigate directly to "/checkout"
    And I click the Place Order button
    Then I should be redirected to "/payment"
    When I enter "<nameOnCard>" into the Name on Card field
    And I enter "<cardNumber>" into the Card Number field
    And I enter "<cvc>" into the CVC field
    And I enter "<expirationMonth>" into the Expiration Month field
    And I enter "<expirationYear>" into the Expiration Year field
    And I click the Pay and Confirm Order button
    Then validation messages should be displayed for required payment fields

    Examples:
      | product  | nameOnCard | cardNumber       | cvc | expirationMonth | expirationYear |
      | Blue Top | Faker      | 4655724338490811 | 906 |               0 |           2027 |
      | Blue Top | Faker      | 4655724338490811 | 906 |              13 |           2027 |
      | Blue Top | Faker      | 4655724338490811 | 906 | a               |           2027 |

  @TC_UI_Checkout_Checkout_013 @ui @checkout @high
  Scenario Outline: Verify user cannot proceed when expiration year is zero, negative or characters
    Given I am on the home page
    And I am logged in on the UI
    And I have added "<product>" to the cart
    When I navigate directly to "/checkout"
    And I click the Place Order button
    Then I should be redirected to "/payment"
    When I enter "<nameOnCard>" into the Name on Card field
    And I enter "<cardNumber>" into the Card Number field
    And I enter "<cvc>" into the CVC field
    And I enter "<expirationMonth>" into the Expiration Month field
    And I enter "<expirationYear>" into the Expiration Year field
    And I click the Pay and Confirm Order button
    Then validation messages should be displayed for required payment fields

    Examples:
      | product  | nameOnCard | cardNumber       | cvc | expirationMonth | expirationYear |
      | Blue Top | Faker      | 4655724338490811 | 906 |              11 |              0 |
      | Blue Top | Faker      | 4655724338490811 | 906 |              11 | a              |

  @TC_UI_Checkout_Checkout_014 @ui @checkout @high
  Scenario Outline: Verify user cannot proceed when card number is zero, negative, or contains characters
    Given I am on the home page
    And I am logged in on the UI
    And I have added "<product>" to the cart
    When I navigate directly to "/checkout"
    And I click the Place Order button
    Then I should be redirected to "/payment"
    When I enter "<nameOnCard>" into the Name on Card field
    And I enter "<cardNumber>" into the Card Number field
    And I enter "<cvc>" into the CVC field
    And I enter "<expirationMonth>" into the Expiration Month field
    And I enter "<expirationYear>" into the Expiration Year field
    And I click the Pay and Confirm Order button
    Then validation messages should be displayed for required payment fields

    Examples:
      | product  | nameOnCard | cardNumber        | cvc | expirationMonth | expirationYear |
      | Blue Top | Faker      | a                 | 906 |              11 |           2027 |
      | Blue Top | Faker      |   999999999999999 | 906 |              11 |           2027 |
      | Blue Top | Faker      | 10000000000000000 | 906 |              11 |           2027 |

  @TC_UI_Checkout_Checkout_015 @ui @checkout @high
  Scenario Outline: Verify user cannot proceed when CVC is zero, negative, or contains characters
    Given I am on the home page
    And I am logged in on the UI
    And I have added "<product>" to the cart
    When I navigate directly to "/checkout"
    And I click the Place Order button
    Then I should be redirected to "/payment"
    When I enter "<nameOnCard>" into the Name on Card field
    And I enter "<cardNumber>" into the Card Number field
    And I enter "<cvc>" into the CVC field
    And I enter "<expirationMonth>" into the Expiration Month field
    And I enter "<expirationYear>" into the Expiration Year field
    And I click the Pay and Confirm Order button
    Then validation messages should be displayed for required payment fields

    Examples:
      | product  | nameOnCard | cardNumber       | cvc  | expirationMonth | expirationYear |
      | Blue Top | Faker      | 4655724338490811 | a    |              11 |           2027 |
      | Blue Top | Faker      | 4655724338490811 |   99 |              11 |           2027 |
      | Blue Top | Faker      | 4655724338490811 | 1000 |              11 |           2027 |
