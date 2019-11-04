Feature: Projects

  Background:
    Given I use the "pivotal" service and the "owner" account
    And I send a "POST" request to "/projects" with json file "json/requestBodyProject.json"
    And I save the response as "P"

  Scenario: PUT Project
    When I send a "PUT" request to "/projects/{P.id}" with json body
    """
    {
    "name": "Project updated by cucumber"
    }
    """
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Project updated by cucumber"
    And I send a "DELETE" request to "/projects/{P.id}"
    And I validate the response has status code 204
