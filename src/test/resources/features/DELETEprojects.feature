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
  Scenario: DELETE Project
    When I send a DELETE request to "/projects/{Project.id}"
    Then I validate the response has status code 204