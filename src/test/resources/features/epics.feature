Feature: Epics

  Scenario: PUT Epic
    Given I send a "POST" request to "/projects"
      | name             | Project created by cucumber |
      | public           | true                        |
      | week_start_day   | Tuesday                     |
      | iteration_length | 2                           |
    And I save the response as "P"
    And I send a "POST" request to "/projects/{P.id}/epics"
      | name | Epic created by cucumber |
    And I save the response as "E"
    When I send a "PUT" request to "/projects/{P.id}/epics/{E.id}"
      | name        | Epic updated by cucumber |
      | description | Epic test description    |
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Epic updated by cucumber"
    And I validate the response contains "description" equals "Epic test description"
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204

  Scenario: POST Epic
    Given I send a "POST" request to "/projects"
      | name             | Project created by cucumber |
      | public           | true                        |
      | week_start_day   | Tuesday                     |
      | iteration_length | 2                           |
    And I save the response as "P"
    When I send a "POST" request to "/projects/{P.id}/epics"
      | name | Epic created by cucumber |
    And I save the response as "E"
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Epic created by cucumber"
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204

  Scenario: GET Epic
    Given I send a "POST" request to "/projects"
      | name             | Project created by cucumber |
      | public           | true                        |
      | week_start_day   | Tuesday                     |
      | iteration_length | 2                           |
    And I save the response as "P"
    And I send a "POST" request to "/projects/{P.id}/epics"
      | name        | Epic created by cucumber |
      | description | Epic test description    |
    And I save the response as "E"
    When I send a GET request to "/projects/{P.id}/epics/{E.id}"
    Then I validate the response contains "name" equals "Epic created by cucumber"
    And I validate the response contains "description" equals "Epic test description"
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204

  Scenario: DELETE Epic
    Given I send a "POST" request to "/projects"
      | name             | Project created by cucumber |
      | public           | true                        |
      | week_start_day   | Tuesday                     |
      | iteration_length | 2                           |
    And I save the response as "P"
    And I send a "POST" request to "/projects/{P.id}/epics"
      | name        | Epic created by cucumber |
      | description | Epic test description    |
    And I save the response as "E"
    When I send a DELETE request to "/projects/{P.id}/epics/{E.id}"
    Then I validate the response has status code 204
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204
