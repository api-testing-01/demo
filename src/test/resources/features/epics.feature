Feature: Epics

    Background:
      Given I send a "POST" request to "/projects" with json body
      """
      {
      "name": "Project created by automation",
      "public": true,
      "week_start_day": "Monday",
      "iteration_length": 3
      }
      """
      And I save the response as "P"
      And I validate the response has status code 200
      And I validate the response contains "name" equals "Project created by automation"

    Scenario: POST Epics
      When I send a "POST" request to "/projects/{P.id}/epics" with json body
      """
      {
      "name": "Configure a Epic",
      "description": "Create and get a epic",
      "label": {"name": "get a epic"}
      }
      """
      Then I save the response as "E"
      And I validate the response has status code 200
      And I validate the response contains "name" equals "Configure a Epic"
      And I validate the response contains "description" equals "Create and get a epic"
      And I send a DELETE request to "/projects/{P.id}/epics/{E.id}"
      And I validate the response has status code 204
      And I send a DELETE request to "/projects/{P.id}"
      And I validate the response has status code 204

    Scenario: GET All Epics
      Given I send a "POST" request to "/projects/{P.id}/epics" with json body
      """
      {
      "name": "Configure a Epic",
      "description": "Create and get a epic",
      "label": {"name": "get a epic"}
      }
      """
      And I save the response as "E"
      And I validate the response has status code 200
      When I send a GET request to "/projects/{P.id}/epics"
      Then I validate the response has status code 200
      And I validate the response contains "name" equals "Configure a Epic"
      And I validate the response contains "description" equals "Create and get a epic"
      And I validate the response contains "label.name" equals "get a epic"
      And I send a DELETE request to "/projects/{P.id}/epics/{E.id}"
      And I validate the response has status code 204
      And I send a DELETE request to "/projects/{P.id}"
      And I validate the response has status code 204

  Scenario: GET Epic
    Given I send a "POST" request to "/projects/{P.id}/epics" with json body
    """
    {
    "name": "Configure a Epic",
    "description": "Create and get a epic",
    "label": {"name": "get a epic"}
    }
    """
    And I save the response as "E"
    And I validate the response has status code 200
    When I send a GET request to "/projects/{P.id}/epics/{E.id}"
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Configure a Epic"
    And I validate the response contains "description" equals "Create and get a epic"
    And I validate the response contains "label.name" equals "get a epic"
    And I send a DELETE request to "/projects/{P.id}/epics/{E.id}"
    And I validate the response has status code 204
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204

  Scenario: PUT Epic
    Given I send a "POST" request to "/projects/{P.id}/epics" with json body
    """
    {
    "name": "Configure a Epic",
    "description": "Create and get a epic",
    "label": {"name": "get a epic"}
    }
    """
    And I save the response as "E"
    And I validate the response has status code 200
    When I send a "PUT" request to "/projects/{P.id}/epics/{E.id}" with json body
    """
    {
    "name": "Updated a Epic by automation"
    }
    """
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Updated a Epic by automation"
    And I validate the response contains "description" equals "Create and get a epic"
    And I validate the response contains "label.name" equals "get a epic"
    And I send a DELETE request to "/projects/{P.id}/epics/{E.id}"
    And I validate the response has status code 204
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204

  Scenario: DELETE Epic
    Given I send a "POST" request to "/projects/{P.id}/epics" with json body
    """
    {
    "name": "Configure a Epic",
    "description": "Create and get a epic",
    "label": {"name": "get a epic"}
    }
    """
    And I save the response as "E"
    And I validate the response has status code 200
    When I send a DELETE request to "/projects/{P.id}/epics/{E.id}"
    Then I validate the response has status code 204
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204