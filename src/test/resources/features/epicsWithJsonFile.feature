Feature: Epics

  Background:
    Given I send a "POST" request to "/projects" with json file "json/requestBodyProject.json"
    And I save the response as "P"
    And I send a "POST" request to "/projects/{P.id}/epics" with json file "json/requestBodyEpic.json"
    And I save the response as "E"

  Scenario: PUT Epic
    When I send a "PUT" request to "/projects/{P.id}/epics/{E.id}" with json body
     """
    {
      "name": "Epic updated by cucumber",
      "description": "description for Epic, updated by cucumber"
    }
    """
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Epic updated by cucumber"
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204

  Scenario: GET Epic
    When I send a GET request to "/projects/{P.id}/epics/{E.id}"
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Epic created by cucumber"
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204

  Scenario: DELETE Epic
    When I send a DELETE request to "/projects/{P.id}/epics/{E.id}"
    Then I validate the response has status code 204
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204