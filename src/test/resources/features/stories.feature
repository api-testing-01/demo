Feature: Stories

  Scenario: PUT Story
    Given I send a "POST" request to "/projects" with json body
    """
    {
    "name": "Project created by cucumber"
    }
    """
    And I save the response as "P"
    And I send a "POST" request to "/projects/{P.id}/stories" with json body
    """
    {
    "name": "Story created by cucumber"
    }
    """
    And I save the response as "S"
    When I send a "PUT" request to "/projects/{P.id}/stories/{S.id}" with json body
    """
    {
    "name": "Story updated by cucumber"
    }
    """
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Story updated by cucumber"
    And I send "DELETE" request to "/projects/{P.id}"
    And I validate the response has status code 204

  Scenario: GET Story
    Given I send a "POST" request to "/projects" with json body
    """
    {
    "name": "Project created by cucumber"
    }
    """
    And I save the response as "P"
    And I send a "POST" request to "/projects/{P.id}/stories" with json body
    """
    {
    "name": "Story created by cucumber"
    }
    """
    And I save the response as "S"
    When I send "GET" request to "/projects/{P.id}/stories/{S.id}"
    Then I validate the response contains "name" equals "Story created by cucumber"
    And I validate the response contains "kind" equals "story"
    And I validate the response contains "story_type" equals "feature"
    And I validate the response contains "current_state" equals "unscheduled"
    And I send "DELETE" request to "/projects/{P.id}"
    And I validate the response has status code 204

  Scenario: POST Story
    Given I send a "POST" request to "/projects" with json body
    """
    {
    "name": "Project created by cucumber"
    }
    """
    And I save the response as "P"
    When I send a "POST" request to "/projects/{P.id}/stories" with json body
    """
    {
    "name": "Story created by cucumber"
    }
    """
    And I save the response as "S"
    Then I validate the response contains "name" equals "Story created by cucumber"
    And I send "DELETE" request to "/projects/{P.id}"
    And I validate the response has status code 204

  Scenario: DELETE Story
    Given I send a "POST" request to "/projects" with json body
    """
    {
    "name": "Project created by cucumber"
    }
    """
    And I save the response as "P"
    When I send a "POST" request to "/projects/{P.id}/stories" with json body
    """
    {
    "name": "Story created by cucumber"
    }
    """
    And I save the response as "S"
    And I send "DELETE" request to "/projects/{P.id}"
    And I validate the response has status code 204
    When I send "GET" request to "/projects/{P.id}/stories/{S.id}"
    Then I validate the response contains "code" equals "unauthorized_operation"
    And I validate the response contains "kind" equals "error"
    And I validate the response contains "error" equals "Authorization failure."
