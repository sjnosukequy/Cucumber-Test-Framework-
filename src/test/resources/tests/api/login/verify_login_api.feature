Feature: Verify Login API
As a tester
I want to validate the verifyLogin API
So that valid registered users can be successfully verified

  @TC_API_Login_Login_013 @api @login @high
  Scenario Outline: Verify verifyLogin API returns success for valid email and password
    When I send a POST login request to "/api/verifyLogin" with form parameters "<email>" and "<password>"
    Then the login response status code should be 200 and the login response message should be "User exists!"

    Examples:
      | email               | password |
      | test1_uni@gmail.com | a        |
      # | test2_uni@gmail.com | a        |

  @TC_API_Login_Login_014 @api @login @high
  Scenario Outline: Verify verifyLogin API returns error for valid email and incorrect password
    When I send a POST login request to "/api/verifyLogin" with form parameters "<email>" and "<password>"
    Then the login response status code should be 404 and the login response message should be "User not found!"

    Examples:
      | email                | password        |
      | test1_uni@gmail.com  | ARANDOMPASSWORD |
      # | test2_uni@gmail.com  | ARANDOMPASSWORD |
      # | testuser_1@gmail.com | ARANDOMPASSWORD |

  @TC_API_Login_Login_015 @api @login @high
  Scenario Outline: Verify verifyLogin API returns error when password is empty
    When I send a POST login request to "/api/verifyLogin" with form parameters "<email>" and "<password>"
    Then the login response status code should be 400 and the login response message should be "Bad request, email or password parameter is missing in POST request."

    Examples:
      | email                | password |
      | test1_uni@gmail.com  |          |
      # | test2_uni@gmail.com  |          |
      # | testuser_1@gmail.com |          |

  @TC_API_Login_Login_016 @api @login @high
  Scenario Outline: Verify verifyLogin API returns error when email is empty
    When I send a POST login request to "/api/verifyLogin" with form parameters "<email>" and "<password>"
    Then the login response status code should be 400 and the login response message should be "Bad request"

    Examples:
      | email | password |
      |       | a        |

  @TC_API_Login_Login_017 @api @login @high
  Scenario Outline: Verify verifyLogin API returns error when both email and password are empty
    When I send a POST login request to "/api/verifyLogin" with form parameters "<email>" and "<password>"
    Then the login response status code should be 400 and the login response message should be "Bad request"

    Examples:
      | email | password |
      |       |          |

  @TC_API_Login_Login_018 @api @login @high
  Scenario Outline: Verify verifyLogin API returns error for invalid email format
    When I send a POST login request to "/api/verifyLogin" with form parameters "<email>" and "<password>"
    Then the login response status code should be 400 and the login response message should be "Bad request"

    Examples:
      | email | password |
      | a     | a        |
