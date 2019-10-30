Feature: Boards

  Scenario: PUT Board
    Given I use the "trello" service
    Given I send a "POST" request to "/boards" with json body
    """
    {
    "name": "Board0001 created by cucumber"
    }
    """
    And I save the response as "P"
    When I send a "PUT" request to "/boards/{P.id}" with json body
    """
    {
    "name": "Board0001 updated by cucumber"
    }
    """
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Board0001 updated by cucumber"
    And I send a DELETE request to "/boards/{P.id}"
    And I validate the response has status code 200
