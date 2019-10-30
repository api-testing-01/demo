Feature: Stories /projects/{project_id}/stories

  Background: pre conditions
    Given I send a "POST" request to "/projects"
      | name             | Project created by cucumber |
      | public           | true                        |
      | week_start_day   | Tuesday                     |
      | iteration_length | 2                           |
    And I save the response as "P"

  @PostStory
  Scenario: Create a Story
    When I send a "POST" request to "/projects/{P.id}/stories"
      | name          | Exhaust ports are ray shielded |
      | current_state | accepted                       |
      | estimate      | 1                              |
      | description   | null                           |
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Exhaust ports are ray shielded"
    And I validate the response contains "current_state" equals "accepted"
    And I validate the response contains "estimate" equals "1"
    And I validate the response contains "description" equals "null"
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204

  @GetStory
  Scenario: Get Stories from a project
    Given I had the following "name" of stories:
      | Story1 |
      | Story2 |
    When I send a GET request to "/projects/{P.id}/stories"
    Then I validate the response has status code 200
    And I validate the response contains "name":
      | Story1 |
      | Story2 |
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204
