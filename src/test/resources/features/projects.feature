Feature: Projects

  Scenario: PUT Project
    Given I send a "POST" request to "/projects" with json body
    """
    {
    "name": "Project created by cucumber",
    "public": true,
    "week_start_day": "Tuesday",
    "iteration_length": 2
    }
    """
    And I save the response as "P"
    When I send a "PUT" request to "/projects/{P.id}" with json body
    """
    {
    "name": "Project updated by cucumber"
    }
    """
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Project updated by cucumber"
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204

  Scenario: POST Project
    Given I send a "POST" request to "/projects" with json body
    """
    {
    "name": "Project POST Test",
    "public": true,
    "week_start_day": "Tuesday",
    "iteration_length": 2
    }
    """
    And I save the response as "P02"
    And I validate the response has status code 200
    Then I validate the response contains "name" equals "Project POST Test"
    And I validate the response contains "public" equals "true"
    And I validate the response contains "week_start_day" equals "Tuesday"
    And I validate the response contains "iteration_length" equals "2"
    And I send a DELETE request to "/projects/{P02.id}"
    And I validate the response has status code 204

  Scenario: DELETE Project
    Given I send a "POST" request to "/projects" with json body
    """
    {
    "name": "Project POST Test",
    "public": true,
    "week_start_day": "Tuesday",
    "iteration_length": 2
    }
    """
    And I save the response as "P03"
    When I send a DELETE request to "/projects/{P03.id}"
    And I validate the response has status code 204
    Then I send a "GET" request to "/projects/{P03.id}"
    And I validate the response has status code 404
