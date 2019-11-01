Feature: Projects

  Scenario: POST Project
    Given I use the "pivotal" service and the "owner" account
    When I send a "POST" request to "/projects" with json body
    """
    {
    "name": "Project updated by cucumber",
    "public": true
    }
    """
    And I save the response as "P"
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Project updated by cucumber"
    And I validate the response contains "public" equals "true"
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204
