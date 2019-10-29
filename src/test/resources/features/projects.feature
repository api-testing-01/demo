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
    "name": "Project created by cucumber",
    "public": true,
    "week_start_day": "Monday",
    "iteration_length": 4,
    "point_scale": "0,1,2,4,8"
    }
    """
    And I save the response as "P"
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Project created by cucumber"
    And I validate the response contains "week_start_day" equals "Monday"
    And I validate the response contains "iteration_length" equals "4"
    And I validate the response contains "point_scale" equals "0,1,2,4,8"
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204

  Scenario: GET Project
    Given I send a "POST" request to "/projects" with json body
    """
    {
    "name": "Project created by cucumber",
    "public": true,
    "week_start_day": "Friday",
    "iteration_length": 2
    }
    """
    And I save the response as "P"
    When I send a GET request to "/projects/{P.id}" with json body
    Then I validate the response contains "name" equals "Project created by cucumber"
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204

  Scenario: Delete Project
    Given I send a "POST" request to "/projects" with json body
    """
    {
    "name": "Project created by cucumber",
    "public": true,
    "week_start_day": "Friday",
    "iteration_length": 2
    }
    """
    And I save the response as "P"
    When I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204