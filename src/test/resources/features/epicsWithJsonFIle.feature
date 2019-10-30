Feature: Stories

  Scenario: POST Epics
    Given I send a "POST" request to "/projects" with json file "json/requestBodyProject.json"
    And I save the response as "P"
    When I send a "POST" request to "/projects/{P.id}/epics" with json file "json/requestBodyEpics.json"
    And I save the response as "S"
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Epictest01"
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204

  Scenario: PUT Epics
    Given I send a "POST" request to "/projects" with json file "json/requestBodyProject.json"
    And I save the response as "P"
    And I send a "POST" request to "/projects/{P.id}/epics" with json file "json/requestBodyEpics.json"
    And I save the response as "S"
    When I send a "PUT" request to "/projects/{P.id}/epics/{S.id}" with json body
        """
    {
    "name": "Epictest01UPDATED"
    }
    """
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Epictest01UPDATED"
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204

  Scenario: GET Epics
    Given I send a "POST" request to "/projects" with json file "json/requestBodyProject.json"
    And I save the response as "P"
    And I send a "POST" request to "/projects/{P.id}/epics" with json file "json/requestBodyEpics.json"
    And I save the response as "S"
    When I send a GET request to "/projects/{P.id}/epics/{S.id}"
    And I save the response as "Z"
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Epictest01"
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204

  Scenario: DELETE Epics
    Given I send a "POST" request to "/projects" with json file "json/requestBodyProject.json"
    And I save the response as "P"
    And I send a "POST" request to "/projects/{P.id}/epics" with json file "json/requestBodyEpics.json"
    And I save the response as "S"
    And I validate the response has status code 200
    And I validate the response contains "name" equals "Epictest01"
    When I send a DELETE request to "/projects/{P.id}/epics/{S.id}"
    Then I validate the response has status code 204
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204