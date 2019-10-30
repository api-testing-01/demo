Feature: Epic operations GET

  Background: Pre-conditions
    Given I send a "POST" request to "/projects"
      | name             | Project created by cucumber |
      | public           | true                        |
      | week_start_day   | Tuesday                     |
      | iteration_length | 2                           |
    And I save the response as "P"
    Given I had the following "name" of "{P.id}/epics":
      | Epic1 |
      | Epic2 |

  @GetEpics
  Scenario: Get all epics
    When I send a GET request to "/projects/{P.id}/epics"
    Then I validate the response has status code 200
    And I validate the response contains "name":
      | Epic1 |
      | Epic2 |
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204
