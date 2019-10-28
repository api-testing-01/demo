Feature: Projects
  Scenario: POST Project
    When I send a "POST" request to "/projects" with json file "json/requestBodyProject.json"
    And I save the response as "P"
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Project created by cucumber"
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204

  Scenario: PUT Project
    Given I send a "POST" request to "/projects" with json file "json/requestBodyProject.json"
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

  Scenario: GET Project
    Given I send a "POST" request to "/projects" with json file "json/requestBodyProject.json"
    And I save the response as "P"
    When I send a GET request to "/projects/{P.id}"
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Project created by cucumber"
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204

  Scenario: DELETE Story
    Given I send a "POST" request to "/projects" with json file "json/requestBodyProject.json"
    And I save the response as "P"
    When I send a DELETE request to "/projects/{P.id}"
    Then I validate the response has status code 204