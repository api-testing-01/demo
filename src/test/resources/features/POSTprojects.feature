Feature: Projects

  @project
  Scenario: POST Project with only name
    When I send a "POST" request to "/projects" with json body
    """
    {
    "name": "Project Automation"
    }
    """
    And I save the response as "Project"
    Then I validate the response has status code 200
    And I validate the response contains "kind" equals "project"
    And I validate the response contains "name" equals "Project Automation"
    And I send a DELETE request to "/projects/{Project.id}"
    And I validate the response has status code 204

  @project
  Scenario: POST Project with custom data
    When I send a "POST" request to "/projects"
      | name             | Project Custom Data         |
      | public           | true                        |
      | week_start_day   | Monday                      |
      | iteration_length | 2                           |
    And I save the response as "Custom"
    Then I validate the response has status code 200
    And I validate the response contains "kind" equals "project"
    And I validate the response contains "name" equals "Project Custom Data"
    And I validate the response contains "public" equals "true"
    And I validate the response contains "week_start_day" equals "Monday"
    And I validate the response contains "iteration_length" equals "2"
    And I send a DELETE request to "/projects/{Custom.id}"
    And I validate the response has status code 204

  @project
  Scenario: POST Project with data from json file
    When I send a "POST" request to "/projects" with json file "json/requestBodyProject.json"
    And I save the response as "PJson"
    Then I validate the response has status code 200
    And I validate the response contains "kind" equals "project"
    And I validate the response contains "name" equals "Project created by cucumber"
    And I validate the response contains "public" equals "true"
    And I validate the response contains "week_start_day" equals "Friday"
    And I validate the response contains "iteration_length" equals "2"
    And I send a DELETE request to "/projects/{PJson.id}"
    And I validate the response has status code 204