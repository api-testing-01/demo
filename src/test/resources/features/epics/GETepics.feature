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
  Scenario: GET Epic
    When I send a GET request to "/projects/{Project.id}/epics"
    Then I validate the response has status code 200
    And I send a DELETE request to "/projects/{Project.id}"
    And I validate the response has status code 204

  Scenario: GET by ID Epic
    When I send a GET request to "/projects/{Project.id}/epics/{Epic.id}"
    Then I validate the response has status code 200
    And I validate the response contains "kind" equals "epic"
    And I validate the response contains "name" equals "Epic Automation"
    And I validate the response contains "project_id" equals "Project.id"
    And I send a DELETE request to "/projects/{Project.id}"
    And I validate the response has status code 204