Feature: Epics
  Background:
    Given I send a "POST" request to "/projects" with json body
    """
    {
    "name": "Project Automation"
    }
    """
    And I save the response as "Project"

  @epic
  Scenario: POST Epic
    When I send a "POST" request to "/projects/{Project.id}/epics" with json body
    """
    {
    "name": "Epic Automation"
    }
    """
    And I save the response as "Epic"
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Epic Automation"
    And I validate the response contains "project_id" equals "Project.id"
    And I send a DELETE request to "/projects/{Project.id}"
    And I validate the response has status code 204

  @epic
  Scenario: POST Epic from data table
    When I send a "POST" request to "/projects/{Project.id}/epics"
    | name  | Epic Automation |
    And I save the response as "Epic"
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Epic Automation"
    And I validate the response contains "project_id" equals "Project.id"
    And I send a DELETE request to "/projects/{Project.id}"
    And I validate the response has status code 204

  @epic
  Scenario: POST Epic from data table
    When I send a "POST" request to "/projects/{Project.id}/epics" with json file "json/requestPostEpic.json"
    And I save the response as "Epic"
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Epic Automation"
    And I validate the response contains "project_id" equals "Project.id"
    And I send a DELETE request to "/projects/{Project.id}"
    And I validate the response has status code 204