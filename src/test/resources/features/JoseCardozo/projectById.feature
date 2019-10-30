Feature: Projects by id

  Background: Create a project
    Given I send a "POST" request to "/projects"
      | name             | Project created by cucumber |
      | public           | true                        |
      | week_start_day   | Tuesday                     |
      | iteration_length | 2                           |
    And I save the response as "P"

  @GetProjectById
  Scenario: Get a project by id
    When I send a GET request to "/projects/{P.id}"
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Project created by cucumber"
    And  I validate the response contains "public" equals "true"
    And  I validate the response contains "week_start_day" equals "Tuesday"
    And  I validate the response contains "iteration_length" equals "2"
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204

  @PutProjectById
  Scenario: Put a project
    When I send a "PUT" request to "/projects/{P.id}"
      | name                   | Project updated |
      | week_start_day         | Monday          |
      | iteration_length       | 4               |
      | velocity_averaged_over | 1               |
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Project updated"
    And  I validate the response contains "week_start_day" equals "Monday"
    And  I validate the response contains "iteration_length" equals "4"
    And  I validate the response contains "velocity_averaged_over" equals "1"
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204