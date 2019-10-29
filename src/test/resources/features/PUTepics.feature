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
  Scenario: PUT Epic
    When I send a "PUT" request to "/projects/{Project.id}/epics/{Epic.id}" with json body
    """
    {
    "name": "New Name From Cucumber",
    "description": "New description added from cucumber steps"
    }
    """
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "New Name From Cucumber"
    And I validate the response contains "description" equals "New description added from cucumber steps"
    And I validate the response contains "project_id" equals "Project.id"
    And I send a DELETE request to "/projects/{Project.id}"
    And I validate the response has status code 204

  @epic
  Scenario: PUT Epic from data table
    When I send a "PUT" request to "/projects/{Project.id}/epics/{Epic.id}"
    | name        | New Name From Cucumber                    |
    | description | New description added from cucumber steps |
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "New Name From Cucumber"
    And I validate the response contains "description" equals "New description added from cucumber steps"
    And I validate the response contains "project_id" equals "Project.id"
    And I send a DELETE request to "/projects/{Project.id}"
    And I validate the response has status code 204

  @epic
  Scenario: PUT Epic from data table
    When I send a "PUT" request to "/projects/{Project.id}/epics/{Epic.id}" with json file "json/requestPutEpic.json"
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "New Name From Json File"
    And I validate the response contains "description" equals "Added description from a json file"
    And I validate the response contains "project_id" equals "Project.id"
    And I send a DELETE request to "/projects/{Project.id}"
    And I validate the response has status code 204