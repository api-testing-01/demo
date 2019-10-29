Feature: Stories

  Scenario: PUT Story
    Given I send a "POST" request to "/projects"
      | name             | Project created by cucumber |
      | public           | true                        |
      | week_start_day   | Tuesday                     |
      | iteration_length | 2                           |
    And I save the response as "P"
    And I send a "POST" request to "/projects/{P.id}/stories"
      | name | Story created by cucumber |
    And I save the response as "S"
    When I send a "PUT" request to "/projects/{P.id}/stories/{S.id}"
      | name | Story updated by cucumber |
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Story updated by cucumber"
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204
