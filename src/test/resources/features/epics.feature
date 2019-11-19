Feature: Epics

  Scenario: GET Epic
    Given I send a "POST" request to "/projects" with json body
    """
    {
      "name": "Project created by Cucumber",
      "public": true,
      "week_start_day": "Monday",
      "iteration_length": 2
    }
    """
    And I save the response as "P"
    And I send a "POST" request to "/projects/{P.id}/epics" with json body
    """
    {
      "name": "Epic created by Cucumber",
      "label": "Label1",
      "description": "Description1"
    }
    """
    And I save the response as "E"
    When I send a GET request to "/projects/{P.id}/epics/{E.id}"
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Epic created by Cucumber"
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204

  Scenario: POST Epic
    Given I send a "POST" request to "/projects" with json body
    """
    {
      "name": "Project created by Cucumber",
      "public": true,
      "week_start_day": "Monday",
      "iteration_length": 2
    }
    """
    And I save the response as "P"
    And I send a "POST" request to "/projects/{P.id}/epics" with json body
    """
    {
      "name": "Epic created by Cucumber",
      "label": "Label1",
      "description": "Description1"
    }
    """
    And I save the response as "E"
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Epic created by Cucumber"
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204

  Scenario: PUT Epic
    Given I send a "POST" request to "/projects" with json body
    """
    {
      "name": "Project created by Cucumber",
      "public": true,
      "week_start_day": "Monday",
      "iteration_length": 2
    }
    """
    And I save the response as "P"
    And I send a "POST" request to "/projects/{P.id}/epics" with json body
    """
    {
      "name": "Epic created by Cucumber",
      "label": "Label1",
      "description": "Description1"
    }
    """
    And I save the response as "E"
    When I send a "PUT" request to "/projects/{P.id}/epics/{E.id}" with json body
    """
    {
    "name": "Epic updated by Cucumber"
    }
    """
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Epic updated by Cucumber"
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204

  Scenario: DELETE Epic
    Given I send a "POST" request to "/projects" with json body
    """
    {
      "name": "Project created by Cucumber",
      "public": true,
      "week_start_day": "Monday",
      "iteration_length": 2
    }
    """
    And I save the response as "P"
    And I send a "POST" request to "/projects/{P.id}/epics" with json body
    """
    {
      "name": "Epic created by Cucumber",
      "label": "Label1",
      "description": "Description1"
    }
    """
    And I save the response as "E"
    When I send a DELETE request to "/projects/{P.id}/epics{E.id}"
    Then I validate the response has status code 204
    And I send a GET request to "/projects/{P.id}/epics/{E.id}"
    And I validate the response contains "code" equals "unauthorized_operation"
    And I validate the response contains "kind" equals "error"
    And I validate the response contains "error" equals "Authorization failure."

