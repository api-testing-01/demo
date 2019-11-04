Feature: Stories

  Scenario: POST Story
    Given I send a "POST" request to "/projects" with json body
    """
    {
    "name": "Project created by cucumber"
    }
    """
    And I save the response as "P"
    And I send a "POST" request to "/projects/{P.id}/epics" with json body
    """
    {
    "name": "Epic created by cucumber"
    }
    """
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Epic created by cucumber"
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204

  Scenario: PUT Story
    Given I send a "POST" request to "/projects" with json body
    """
    {
    "name": "Project created by cucumber"
    }
    """
    And I save the response as "P"
    And I send a "POST" request to "/projects/{P.id}/epics" with json body
    """
    {
    "name": "Epic created by cucumber"
    }
    """
    And I save the response as "E"
    When I send a "PUT" request to "/projects/{P.id}/epics/{E.id}" with json body
    """
    {
    "name": "Epic updated by cucumber"
    }
    """
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Epic updated by cucumber"
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204

  Scenario: DELETE Epic
    Given I send a "POST" request to "/projects" with json body
    """
    {
    "name": "Project created by cucumber"
    }
    """
    And I save the response as "P"
    When I send a "POST" request to "/projects/{P.id}/epics" with json body
    """
    {
    "name": "Epic created by cucumber"
    }
    """
    And I save the response as "E"
    And I send a DELETE request to "/projects/{P.id}/epics/{E.id}"
    And I validate the response has status code 204
    When I send a GET request to "/projects/{P.id}/epics/{E.id}"
    Then I validate the response contains "code" equals "unfound_resource"
    And I validate the response contains "kind" equals "error"
    And I validate the response contains "error" equals "The object you tried to access could not be found.  It may have been removed by another user, you may be using the ID of another object type, or you may be trying to access a sub-resource at the wrong point in a tree."
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204

  Scenario: GET Epic
    Given I send a "POST" request to "/projects" with json body
    """
    {
    "name": "Project created by cucumber"
    }
    """
    And I save the response as "P"
    And I send a "POST" request to "/projects/{P.id}/epics" with json body
    """
    {
    "name": "Epic created by cucumber"
    }
    """
    And I save the response as "S"
    When I send a GET request to "/projects/{P.id}/epics/{S.id}"
    Then I validate the response contains "name" equals "Epic created by cucumber"
    And I validate the response contains "kind" equals "epic"
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204