Feature: Verify Login UI
  As a tester
  I want to validate the login functionality on the UI
  So that users can log in correctly and invalid inputs are properly handled

  @TC_UI_Login_Login_001 @ui @login @high
  Scenario Outline: Verify user can log in successfully with registered email and correct password
    Given I am on the home page
    Then I am on the login page
    When I enter "<email>" into the login Email Address field
    And I enter "<password>" into the login Password field
    And I click the Login button
    Then the login should be successful
    And I should be redirected to the homepage

    Examples:
      | email               | password |
      | test1_uni@gmail.com | a        |

  @TC_UI_Login_Login_002 @ui @login @high
  Scenario Outline: Verify user cannot log in with registered email and incorrect password
    Given I am on the home page
    Then I am on the login page
    When I enter "<email>" into the login Email Address field
    And I enter "<password>" into the login Password field
    And I click the Login button
    Then an error message should be displayed

    Examples:
      | email               | password     |
      | test1_uni@gmail.com | WrongPass123 |

  @TC_UI_Login_Login_003 @ui @login @high
  Scenario Outline: Verify user cannot log in with registered email and empty password
    Given I am on the home page
    Then I am on the login page
    When I enter "<email>" into the login Email Address field
    And I click the Login button
    Then an error message should be displayed at login password field "<message>"

    Examples:
      | email               | message                     |
      | test1_uni@gmail.com | Please fill out this field. |

  @TC_UI_Login_Login_004 @ui @login @high
  Scenario Outline: Verify user cannot log in with unregistered valid-format email and correct password
    Given I am on the home page
    Then I am on the login page
    When I enter "<email>" into the login Email Address field
    And I enter "<password>" into the login Password field
    And I click the Login button
    Then an error message should be displayed

    Examples:
      | email                  | password |
      | unregistered@email.com | a        |

  @TC_UI_Login_Login_005 @ui @login @medium
  Scenario Outline: Verify user cannot log in with unregistered valid-format email and incorrect password
    Given I am on the home page
    Then I am on the login page
    When I enter "<email>" into the login Email Address field
    And I enter "<password>" into the login Password field
    And I click the Login button
    Then an error message should be displayed

    Examples:
      | email                  | password     |
      | unregistered@email.com | WrongPass123 |

  @TC_UI_Login_Login_006 @ui @login @medium
  Scenario Outline: Verify user cannot log in with unregistered valid-format email and empty password
    Given I am on the home page
    Then I am on the login page
    When I enter "<email>" into the login Email Address field
    And I click the Login button
    Then an error message should be displayed at login password field "<message>"

    Examples:
      | email                  | message                     |
      | unregistered@email.com | Please fill out this field. |

  @TC_UI_Login_Login_007 @ui @login @medium
  Scenario Outline: Verify user cannot log in with invalid email format and correct password
    Given I am on the home page
    Then I am on the login page
    When I enter "<email>" into the login Email Address field
    And I enter "<password>" into the login Password field
    And I click the Login button
    Then an error message should be displayed at login email field "<message>"

    Examples:
      | email         | password | message                                     |
      | invalid email | a        | Please include an '@' in the email address. |

  @TC_UI_Login_Login_008 @ui @login @medium
  Scenario Outline: Verify user cannot log in with invalid email format and incorrect password
    Given I am on the home page
    Then I am on the login page
    When I enter "<email>" into the login Email Address field
    And I enter "<password>" into the login Password field
    And I click the Login button
    Then an error message should be displayed at login email field "<message>"

    Examples:
      | email          | password     | message                           |
      | invalid email@ | WrongPass123 | Please enter a part following '@' |

  @TC_UI_Login_Login_009 @ui @login @medium
  Scenario Outline: Verify user cannot log in with invalid email format and empty password
    Given I am on the home page
    Then I am on the login page
    When I enter "<email>" into the login Email Address field
    And I click the Login button
    Then an error message should be displayed at login email field "<message>"

    Examples:
      | email           | message                                              |
      | invalid email@@ | A part followed by '@' should not contain the symbol |

  @TC_UI_Login_Login_010 @ui @login @medium
  Scenario Outline: Verify user cannot log in with empty email and correct password
    Given I am on the home page
    Then I am on the login page
    When I enter "<password>" into the login Password field
    And I click the Login button
    Then an error message should be displayed at login email field "<message>"

    Examples:
      | password | message                     |
      | a        | Please fill out this field. |

  @TC_UI_Login_Login_011 @ui @login @medium
  Scenario Outline: Verify user cannot log in with empty email and incorrect password
    Given I am on the home page
    Then I am on the login page
    When I enter "<password>" into the login Password field
    And I click the Login button
    Then an error message should be displayed at login email field "<message>"

    Examples:
      | password     | message                     |
      | WrongPass123 | Please fill out this field. |

  @TC_UI_Login_Login_012 @ui @login @medium
  Scenario: Verify user cannot log in with empty email and empty password
    Given I am on the home page
    Then I am on the login page
    And I click the Login button
    Then an error message should be displayed at login email field "<message>"
    And an error message should be displayed at login password field "<message>"

    Examples:
      | message                     |
      | Please fill out this field. |
