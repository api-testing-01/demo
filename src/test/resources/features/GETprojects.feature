Feature: Projects

  Background:
    Given I send a "POST" request to "/projects" with json body
    """
    {
    "name": "Project Automation"
    }
    """
    And I save the response as "Project"

  @project
  Scenario: GET Project
    When I send a GET request to "/projects"
    Then I validate the response has status code 200
    And I send a DELETE request to "/projects/{Project.id}"
    And I validate the response has status code 204

  @project
  Scenario: GET Project by ID
    When I send a GET request to "/projects/{Project.id}"
    Then I validate the response has status code 200
    And I validate the response contains "kind" equals "project"
    And I validate the response contains "name" equals "Project Automation"
    And I send a DELETE request to "/projects/{Project.id}"
    And I validate the response has status code 204
