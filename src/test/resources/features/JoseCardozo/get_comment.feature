Feature: /projects/{project_id}/stories/{story_id}/comments

  Background: Pre-conditions
    Given I send a "POST" request to "/projects"
      | name             | Project created by cucumber |
      | public           | true                        |
      | week_start_day   | Tuesday                     |
      | iteration_length | 2                           |
    And I save the response as "P"
    And I send a "POST" request to "/projects/{P.id}/stories"
      | name | Story1 |
    And I save the response as "S"
    And I send a "POST" request to "/projects/{P.id}/stories/{S.id}/comments"
      | text | This is a new comment to test this endpoint |
    And I save the response as "C"

  @GetComment
  Scenario: Get comment
    When I send a GET request to "/projects/{P.id}/stories/{S.id}/comments"
    Then I validate the response has status code 200
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204
