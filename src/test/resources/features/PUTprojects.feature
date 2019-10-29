Feature: Projects

  Background:
    Given I send a "POST" request to "/projects" with json body
    """
    {
    "name": "Project Automation"
    }
    """
    And I save the response as "Project"

  @project
  Scenario: PUT Project name changed
    When I send a "PUT" request to "/projects/{Project.id}" with json body
    """
    {
    "name": "Project Name Updated"
    }
    """
    And I validate the response has status code 200
    And I validate the response contains "name" equals "Project Name Updated"
    And I send a DELETE request to "/projects/{Project.id}"
    And I validate the response has status code 204

  @project
  Scenario: PUT Project data changed with data table
    When I send a "PUT" request to "/projects/{Project.id}"
      | name             | Project Name Updated        |
      | week_start_day   | Sunday                      |
      | iteration_length | 3                           |
    And I validate the response has status code 200
    And I validate the response contains "name" equals "Project Name Updated"
    And I validate the response contains "week_start_day" equals "Sunday"
    And I validate the response contains "iteration_length" equals "3"
    And I send a DELETE request to "/projects/{Project.id}"
    And I validate the response has status code 204

  @project
  Scenario: PUT Project with data from json file
    When I send a "PUT" request to "/projects/{Project.id}" with json file "json/requestPutBodyProject.json"
    And I validate the response has status code 200
    And I validate the response contains "name" equals "Project Updated by cucumber"
    And I validate the response contains "description" equals "changed by cucumber steps"
    And I validate the response contains "week_start_day" equals "Friday"
    And I validate the response contains "iteration_length" equals "4"
    And I send a DELETE request to "/projects/{Project.id}"
    And I validate the response has status code 204