Feature: Verify Login API
As a tester
I want to validate the verifyLogin API
So that valid registered users can be successfully verified

  @TC_API_Login_Login_013 @api @login @high
  Scenario Outline: Verify verifyLogin API returns success for valid email and password
    When I send a POST request to "/api/verifyLogin" with form parameters "<email>" and "<password>"
    Then the response status code should be 200
    And the response message should be "User exists!"

    Examples:
      | email               | password |
      | test1_uni@gmail.com | a        |
      | test2_uni@gmail.com | a        |

  @TC_API_Login_Login_014 @api @login @high
  Scenario Outline: Verify verifyLogin API returns error for valid email and incorrect password
    When I send a POST request to "/api/verifyLogin" with form parameters "<email>" and "<password>"
    Then the response status code should be 404
    And the response message should be "User not found!"

    Examples:
      | email               | password        |
      | test1_uni@gmail.com | ARANDOMPASSWORD |
      | test2_uni@gmail.com | ARANDOMPASSWORD |

  @TC_API_Login_Login_015 @api @login @high
  Scenario Outline: Verify verifyLogin API returns error when password is empty
    When I send a POST request to "/api/verifyLogin" with form parameters "<email>" and "<password>"
    Then the response status code should be 400
    And the response message should be "Bad request, email or password parameter is missing in POST request."

    Examples:
      | email               | password |
      | test1_uni@gmail.com |          |
      | test2_uni@gmail.com |          |
