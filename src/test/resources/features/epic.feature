Feature: Epics

  Background:
    Given  I send a "POST" request to "/projects" with json body
    """
    {
      "name": "Project created by Cucumber",
      "public": true,
      "week_start_day": "Monday",
      "iteration_length": 2
    }
    """
    And I save the response as "P"

  Scenario: 01 Post a Epic
    Given I send a "POST" request to "/projects/{P.id}/epics" with json body
    """
    {
      "name": "Epic by Cucumber",
      "label": "Label",
      "description": "epic created by cucumber"
    }
    """
    And I save the response as "E"
    When I send a GET request to "/projects/{P.id}/epics/{E.id}"
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Epic by Cucumber"
    And I validate the response contains "label" equals "Label"
    And I validate the response contains "name" equals "epic created by cucumber"
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204

  Scenario: 02 PUT Epic
    Given I send a "POST" request to "/projects/{P.id}/epics" with json body
    """
    {
      "name": "Epic 02",
      "label": "Label",
      "description": "Description"
    }
    """
    And I save the response as "E"
    When I send a "PUT" request to "/projects/{P.id}/epics/{E.id}" with json body
    """
    {
    "name": "Epic edited Cucumber"
    }
    """
    And I validate the response has status code 200
    Then I send a GET request to "/projects/{P.id}/epics/{E.id}"
    And I validate the response contains "name" equals "Epic edited Cucumber"
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204

  Scenario: 03 DELETE Epic
    Given I send a "POST" request to "/projects/{P.id}/epics" with json body
    """
    {
      "name": "Epic 03",
      "label": "Label",
      "description": "Description"
    }
    """
    And I save the response as "E"
    When I send a DELETE request to "/projects/{P.id}/epics{E.id}"
    Then I validate the response has status code 204
    And I send a GET request to "/projects/{P.id}/epics/{E.id}"
    And I validate the response contains "code" equals "unauthorized_operation"
    And I validate the response contains "kind" equals "error"
    And I validate the response contains "error" equals "Authorization failure."
