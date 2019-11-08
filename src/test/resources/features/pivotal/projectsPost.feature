Feature: Projects

  @cleanData
  Scenario: POST Project
    Given I use the "pivotal" service and the "owner" account
    When I send a "POST" request to "/projects" with json body
    """
    {
    "name": "Project updated by cucumber",
    "public": true,
    "new_account_name": "New Account"
    }
    """
    And I save the request endpoint for deleting
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Project updated by cucumber"
    And I validate the response contains "public" equals "true"
