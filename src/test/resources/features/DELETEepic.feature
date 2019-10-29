Feature: Epics
  Background:
    Given I send a "POST" request to "/projects" with json body
    """
    {
    "name": "Project Automation"
    }
    """
    And I save the response as "Project"
    And I send a "POST" request to "/projects/{Project.id}/epics" with json body
    """
    {
    "name": "Epic Automation"
    }
    """
    And I save the response as "Epic"

  @epic
  Scenario: DELETE epic
    When I send a DELETE request to "/projects/{Project.id}/epics/{Epic.id}"
    Then I validate the response has status code 204
    And I send a DELETE request to "/projects/{Project.id}"
    And I validate the response has status code 204