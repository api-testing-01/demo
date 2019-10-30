Feature: Accounts

  Scenario: PATCH Accounts
    Given I use the "sfdc" service
    Given I send a "POST" request to "/sobjects/Account" with json body
    """
    {
    "Name": "Account0002 created by cucumber"
    }
    """
    And I save the response as "P"
    When I send a "PATCH" request to "/sobjects/Account/{P.id}" with json body
    """
    {
    "Name": "Account0002 updated by cucumber"
    }
    """
    Then I validate the response has status code 204
    And I send a DELETE request to "/sobjects/Account/{P.id}"
    And I validate the response has status code 204
