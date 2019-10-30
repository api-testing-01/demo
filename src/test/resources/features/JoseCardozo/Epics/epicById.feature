Feature: Epic by id operations

  Background: Pre-conditions
    Given I send a "POST" request to "/projects"
      | name             | Project created by cucumber |
      | public           | true                        |
      | week_start_day   | Tuesday                     |
      | iteration_length | 2                           |
    And I save the response as "P"
    When I send a "POST" request to "/projects/{P.id}/epics"
      | name | Epic 1 |
    And I save the response as "E"

  @GetEpicById
  Scenario: Get an epics by Id
    When I send a GET request to "/projects/{P.id}/epics/{E.id}"
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Epic 1"
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204

  @PutEpic
  Scenario: Update an epic
    When I send a "PUT" request to "/projects/{P.id}/epics/{E.id}" with json body
    """
    {
      "description":"I update the epic using its api",
      "label":
      {
        "name":"updating"
      },
      "name":"epic updated"
    }
    """
    Then I validate the response has status code 200
    And I validate the response contains "description" equals "I update the epic using its api"
    And I validate the response contains "label.name" equals "updating"
    And I validate the response contains "name" equals "epic updated"
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204

  @DeleteEpic
  Scenario: Delete an epic
    When I send a DELETE request to "/projects/{P.id}/epics/{E.id}"
    Then I validate the response has status code 204
    And I send a GET request to "/projects/{P.id}/epics/{E.id}"
    And I validate the response has status code 404
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204