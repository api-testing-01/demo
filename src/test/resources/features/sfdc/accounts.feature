Feature: Accounts

  Background:
    Given I use the "sfdc" service and the "admin" account
    And I send a POST request to "/sobjects/Account" with json body
    """
    {
    "Name": "Account0002 created by cucumber"
    }
    """
    And I save the response as "P"

  Scenario: PATCH Accounts
    When I send a PATCH request to "/sobjects/Account/{P.id}" with json body
    """
    {
    "Name": "Account0002 updated by cucumber"
    }
    """
    Then I validate the response has status code 204
    And I send a DELETE request to "/sobjects/Account/{P.id}"
    And I validate the response has status code 204
