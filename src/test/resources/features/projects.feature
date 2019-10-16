Feature: Projects

  Scenario: PUT Project
    Given I send a "POST" request to "/projects"
    """
    {
    "name": "Project created by cucumber"
    }
    """
    When I send a "PUT" request to "/projects/{projectId}"
    """
    {
    "name": "Project updated by cucumber"
    }
    """
    Then I validate the response has status code "200"
    And I validate the response contains "name" equals "Project updated by cucumber"
    And I send a DELETE request to "/projects/{projectId}"
    And I validate the response has status code "204"

  Scenario: DELETE Project
    Given I send a "POST" request to "/projects"
    """
    {
    "name": "Project created to be deleted by cucumber"
    }
    """
    When I send a DELETE request to "/projects/{projectId}"
    Then I validate the response has status code "204"

  Scenario: GET Project
    Given I send a "POST" request to "/projects"
    """
    {
    "name": "Project created to get by cucumber"
    }
    """
    When I send a GET request to "/projects/{projectId}"
    Then I validate the response contains "name" equals "Project created to get by cucumber"
    And I validate the response has status code "200"
    And I send a DELETE request to "/projects/{projectId}"
    And I validate the response has status code "204"
