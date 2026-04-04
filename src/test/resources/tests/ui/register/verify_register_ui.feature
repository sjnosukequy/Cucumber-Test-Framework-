Feature: Verify Registration UI
  As a tester
  I want to validate the registration functionality on the UI
  So that guest users can register successfully and invalid sign-up data is handled correctly

  @TC_UI_Registration_FollowUp_001 @ui @registration @high
  Scenario Outline: Verify guest user can continue registration with valid name and unique email
    Given I am on the home page
    When I navigate directly to "/login"
    And I enter "<name>" into the signup Name field
    And I enter "<email>" into the signup Email field
    And I click the Signup button
    Then I should be redirected to "/signup"

    Examples:
      | name        | email                   |
      | John Tester | john.tester001@test.com |

  @TC_UI_Registration_FollowUp_002 @ui @registration @high @donotCleanup
  Scenario Outline: Verify guest user cannot proceed using an existing email address
    Given I am on the home page
    When I navigate directly to "/login"
    And I enter "<name>" into the signup Name field
    And I enter "<email>" into the signup Email field
    And I click the Signup button
    And a signup email error message should be displayed

    Examples:
      | name          | email               |
      | Existing User | test1_uni@gmail.com |

  @TC_UI_Registration_FollowUp_003 @ui @registration @high
  Scenario Outline: Verify guest users cannot create a new account when inputting an invalid format email
    Given I am on the home page
    When I navigate directly to "/login"
    And I enter "<name>" into the signup Name field
    And I enter "<email>" into the signup Email field
    And I click the Signup button
    And validation messages should be displayed for register email field

    Examples:
      | name               | email                 |
      | Invalid Email User | invalidemail.test.com |
      | Invalid Email User | sam@                  |
      | Invalid Email User | @test.com             |
      | Invalid Email User | invalidemail          |

  @TC_UI_Registration_FollowUp_004 @ui @registration @high
  Scenario Outline: Verify guest users see an error message when one or more mandatory fields name and email are empty at the sign-up page
    Given I am on the home page
    When I navigate directly to "/login"
    And I enter "<name>" into the signup Name field
    And I enter "<email>" into the signup Email field
    And I click the Signup button
    And validation messages should be displayed for register email field

    Examples:
      | name         | email                   |
      |              | john.tester001@test.com |
      | Invalid User |                         |

  @TC_UI_Registration_Register_005 @ui @registration @high
  Scenario Outline: Verify guest users can create a new account when inputting all valid information at the additional sign-up page
    Given I am on the home page
    When I navigate directly to "/login"
    And I enter "<name>" into the signup Name field
    And I enter "<email>" into the signup Email field
    And I click the Signup button
    Then I should be redirected to "/signup"
    When I enter "<password>" into the signup Password field
    And I enter "<FName>" into the signup First Name field
    And I enter "<LName>" into the signup Last Name field
    And I enter "<Address1>" into the signup Address Line 1 field
    And I enter "<Address2>" into the signup Address Line 2 field
    And I enter "<State>" into the signup State field
    And I enter "<City>" into the signup City field
    And I enter "<Zipcode>" into the signup Zipcode field
    And I enter "<Mobile>" into the signup Mobile Number field
    And I click the Create Account button
    Then I should be redirected to "/account_created"

    Examples:
      | name        | email                   | password     | FName | LName  | Address1        | Address2 | State | City | Zipcode | Mobile     |
      | John Tester | john.tester001@test.com | Password@123 | John  | Tester | 123 Test Street | Add2     | State | City |  700000 | 0912345678 |

  @TC_UI_Registration_Register_006 @ui @registration @high
  Scenario Outline: Verify guest users see error messages when required fields are empty on additional sign-up page
    Given I am on the home page
    When I navigate directly to "/login"
    And I enter "<name>" into the signup Name field
    And I enter "<email>" into the signup Email field
    And I click the Signup button
    Then I should be redirected to "/signup"
    When I enter "<password>" into the signup Password field
    And I enter "<FName>" into the signup First Name field
    And I enter "<LName>" into the signup Last Name field
    And I enter "<Address1>" into the signup Address Line 1 field
    And I enter "<Address2>" into the signup Address Line 2 field
    And I enter "<State>" into the signup State field
    And I enter "<City>" into the signup City field
    And I enter "<Zipcode>" into the signup Zipcode field
    And I enter "<Mobile>" into the signup Mobile Number field
    And I click the Create Account button
    And validation messages should be displayed for register email field

    Examples:
      | name        | email                   | password     | FName | LName  | Address1        | Address2 | State | City | Zipcode | Mobile     |
      | John Tester | john.tester001@test.com |              | John  | Tester | 123 Test Street | Add2     | State | City |  700000 | 0912345678 |
      | John Tester | john.tester001@test.com | Password@123 |       | Tester | 123 Test Street | Add2     | State | City |  700000 | 0912345678 |
      | John Tester | john.tester001@test.com | Password@123 | John  |        | 123 Test Street | Add2     | State | City |  700000 | 0912345678 |
      | John Tester | john.tester001@test.com | Password@123 | John  | Tester |                 | Add2     | State | City |  700000 | 0912345678 |
      | John Tester | john.tester001@test.com | Password@123 | John  | Tester | 123 Test Street | Add2     |       | City |  700000 | 0912345678 |
      | John Tester | john.tester001@test.com | Password@123 | John  | Tester | 123 Test Street | Add2     | State |      |  700000 | 0912345678 |
      | John Tester | john.tester001@test.com | Password@123 | John  | Tester | 123 Test Street | Add2     | State | City |         | 0912345678 |
      | John Tester | john.tester001@test.com | Password@123 | John  | Tester | 123 Test Street | Add2     | State | City |  700000 |            |

  @TC_UI_Registration_Register_007 @ui @registration @high
  Scenario Outline: Verify user is automatically logged in after successful account creation
    Given I am on the home page
    When I navigate directly to "/login"
    And I enter "<name>" into the signup Name field
    And I enter "<email>" into the signup Email field
    And I click the Signup button
    Then I should be redirected to "/signup"
    When I enter "<password>" into the signup Password field
    And I enter "<FName>" into the signup First Name field
    And I enter "<LName>" into the signup Last Name field
    And I enter "<Address1>" into the signup Address Line 1 field
    And I enter "<Address2>" into the signup Address Line 2 field
    And I enter "<State>" into the signup State field
    And I enter "<City>" into the signup City field
    And I enter "<Zipcode>" into the signup Zipcode field
    And I enter "<Mobile>" into the signup Mobile Number field
    And I click the Create Account button
    Then I should be redirected to "/account_created"
    And I click the continue button on the account created page
    Then I should be redirected to "/"
    And verify that i am logged in on the UI

    Examples:
      | name        | email                   | password     | FName | LName  | Address1        | Address2 | State | City | Zipcode | Mobile     |
      | John Tester | john.tester001@test.com | Password@123 | John  | Tester | 123 Test Street | Add2     | State | City |  700000 | 0912345678 |
