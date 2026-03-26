Feature: Verify Register API
  As a tester
  I want to validate the createAccount API
  So that user registration behaves correctly for different input conditions

  @TC_API_Registration_Register_008 @api @register @high @donotCleanup
  Scenario Outline: Verify Register API creates account successfully with valid data
    When I send a POST register request to "/api/createAccount" with form parameters "<user>"
    Then the register response status code should be 201
    And the register response message should be "User created!"

    Examples:
      | user                                                                                                                                                                                                                                                                |
      | name=John;email=john.valid1@testmail.com;password=123456;title=Mr;birth_date=1;birth_month=January;birth_year=1990;firstname=John;lastname=Doe;company=ABC;address1=Addr1;address2=Addr2;country=USA;zipcode=12345;state=NY;city=NYC;mobile_number=1234567890       |
      | name=Mike;email=mike.valid2@testmail.com;password=pass789;title=Mr;birth_date=10;birth_month=May;birth_year=1988;firstname=Mike;lastname=Ross;company=Pearson;address1=Street1;address2=Apt2;country=USA;zipcode=10001;state=NY;city=NYC;mobile_number=9998887777   |
      | name=Anna;email=anna.valid3@testmail.com;password=abc123;title=Mrs;birth_date=15;birth_month=June;birth_year=1995;firstname=Anna;lastname=Taylor;company=TechCorp;address1=AddrA;address2=AddrB;country=USA;zipcode=90001;state=CA;city=LA;mobile_number=8887776666 |

  @TC_UI_Registration_Register_009 @api @register @high
  Scenario Outline: Verify Register API rejects account creation with existing email
    When I send a POST register request to "/api/createAccount" with form parameters "<user>"
    Then the register response status code should be 400
    And the register response message should be "<error_message>"

    Examples:
      | user                                                                                                                                                                                                                                                                | error_message         |
      | name=John;email=john.valid1@testmail.com;password=123456;title=Mr;birth_date=1;birth_month=January;birth_year=1990;firstname=John;lastname=Doe;company=ABC;address1=Addr1;address2=Addr2;country=USA;zipcode=12345;state=NY;city=NYC;mobile_number=1234567890       | Email already exists! |
      | name=Mike;email=mike.valid2@testmail.com;password=pass789;title=Mr;birth_date=10;birth_month=May;birth_year=1988;firstname=Mike;lastname=Ross;company=Pearson;address1=Street1;address2=Apt2;country=USA;zipcode=10001;state=NY;city=NYC;mobile_number=9998887777   | Email already exists! |
      | name=Anna;email=anna.valid3@testmail.com;password=abc123;title=Mrs;birth_date=15;birth_month=June;birth_year=1995;firstname=Anna;lastname=Taylor;company=TechCorp;address1=AddrA;address2=AddrB;country=USA;zipcode=90001;state=CA;city=LA;mobile_number=8887776666 | Email already exists! |

  @TC_UI_Registration_Register_010 @api @register @high
  Scenario Outline: Verify Register API rejects invalid email format
    When I send a POST register request to "/api/createAccount" with form parameters "<user>"
    Then the register response status code should be 400
    And the register response message should be "<error_message>"

    Examples:
      | user                                                                                                                                                                                                                                                    | error_message        |
      | name=Bob;email=invalidemail;password=123456;title=Mr;birth_date=3;birth_month=March;birth_year=1993;firstname=Bob;lastname=Brown;company=DEF;address1=Addr1;address2=Addr2;country=USA;zipcode=67890;state=TX;city=Dallas;mobile_number=1122334455      | Invalid email format |
      | name=Sam;email=sam@;password=pass123;title=Mr;birth_date=6;birth_month=April;birth_year=1989;firstname=Sam;lastname=Wilson;company=Marvel;address1=AddrS;address2=AddrT;country=USA;zipcode=33333;state=WA;city=Seattle;mobile_number=5554443333        | Invalid email format |
      | name=Eve;email=@test.com;password=eve123;title=Mrs;birth_date=9;birth_month=August;birth_year=1994;firstname=Eve;lastname=Adams;company=Cyber;address1=AddrE;address2=AddrF;country=USA;zipcode=44444;state=NV;city=Vegas;mobile_number=4443332222      | Invalid email format |
      | name=Leo;email=leo.test.com;password=leo123;title=Mr;birth_date=12;birth_month=September;birth_year=1990;firstname=Leo;lastname=King;company=Zoo;address1=AddrK;address2=AddrL;country=USA;zipcode=55555;state=IL;city=Chicago;mobile_number=3332221111 | Invalid email format |

  @TC_UI_Registration_Register_011 @api @register @high
  Scenario Outline: Verify Register API rejects request when mandatory parameters are missing
    When I send a POST register request to "/api/createAccount" with form parameters "<user>"
    Then the register response status code should be 400
    And the register response message should be "<error_message>"

    Examples:
      | user                                                                                                                                                                                                                                                  | error_message                                           |
      | name=;email=user1@testmail.com;password=123456;title=Mr;birth_date=1;birth_month=January;birth_year=1990;firstname=John;lastname=Doe;company=ABC;address1=Addr1;address2=Addr2;country=USA;zipcode=12345;state=NY;city=NYC;mobile_number=1234567890   | Bad request, name parameter is missing in POST request. |
      | name=John;email=;password=123456;title=Mr;birth_date=1;birth_month=January;birth_year=1990;firstname=John;lastname=Doe;company=ABC;address1=Addr1;address2=Addr2;country=USA;zipcode=12345;state=NY;city=NYC;mobile_number=1234567890                 | Bad request, email parameter is missing in POST request. |
      | name=John;email=user3@testmail.com;password=;title=Mr;birth_date=1;birth_month=January;birth_year=1990;firstname=John;lastname=Doe;company=ABC;address1=Addr1;address2=Addr2;country=USA;zipcode=12345;state=NY;city=NYC;mobile_number=1234567890     | Bad request, password parameter is missing in POST request. |
      | name=John;email=user4@testmail.com;password=123456;title=Mr;birth_date=1;birth_month=January;birth_year=1990;firstname=;lastname=Doe;company=ABC;address1=Addr1;address2=Addr2;country=USA;zipcode=12345;state=NY;city=NYC;mobile_number=1234567890   | Bad request, firstname parameter is missing in POST request. |
      | name=John;email=user5@testmail.com;password=123456;title=Mr;birth_date=1;birth_month=January;birth_year=1990;firstname=John;lastname=;company=ABC;address1=Addr1;address2=Addr2;country=USA;zipcode=12345;state=NY;city=NYC;mobile_number=1234567890  | Bad request, lastname parameter is missing in POST request. |
      | name=John;email=user6@testmail.com;password=123456;title=Mr;birth_date=1;birth_month=January;birth_year=1990;firstname=John;lastname=Doe;company=ABC;address1=;address2=Addr2;country=USA;zipcode=12345;state=NY;city=NYC;mobile_number=1234567890    | Bad request, address1 parameter is missing in POST request. |
      | name=John;email=user7@testmail.com;password=123456;title=Mr;birth_date=1;birth_month=January;birth_year=1990;firstname=John;lastname=Doe;company=ABC;address1=Addr1;address2=Addr2;country=;zipcode=12345;state=NY;city=NYC;mobile_number=1234567890  | Bad request, country parameter is missing in POST request. |
      | name=John;email=user8@testmail.com;password=123456;title=Mr;birth_date=1;birth_month=January;birth_year=1990;firstname=John;lastname=Doe;company=ABC;address1=Addr1;address2=Addr2;country=USA;zipcode=12345;state=;city=NYC;mobile_number=1234567890 | Bad request, state parameter is missing in POST request. |
      | name=John;email=user9@testmail.com;password=123456;title=Mr;birth_date=1;birth_month=January;birth_year=1990;firstname=John;lastname=Doe;company=ABC;address1=Addr1;address2=Addr2;country=USA;zipcode=;state=NY;city=NYC;mobile_number=1234567890    | Bad request, zipcode parameter is missing in POST request. |
      | name=John;email=user10@testmail.com;password=123456;title=Mr;birth_date=1;birth_month=January;birth_year=1990;firstname=John;lastname=Doe;company=ABC;address1=Addr1;address2=Addr2;country=USA;zipcode=12345;state=NY;city=;mobile_number=1234567890 | Bad request, city parameter is missing in POST request. |
      | name=John;email=user11@testmail.com;password=123456;title=Mr;birth_date=1;birth_month=January;birth_year=1990;firstname=John;lastname=Doe;company=ABC;address1=Addr1;address2=Addr2;country=USA;zipcode=12345;state=NY;city=NYC;mobile_number=        | Bad request, mobile_number parameter is missing in POST request. |
