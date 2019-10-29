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
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204

  Scenario: POST Story
    Given I send a "POST" request to "/projects" with json body
    """
    {
    "name": "Project created by cucumber API",
    "description": "Story created by Cucumber API framework",
    "story_type": "bug",
    "current_state": "started",
    "estimate": 3
    }
    """
    When I validate the response has status code 200
    And I save the response as "P01"
    Then I validate the response contains "name" equals "Project created by cucumber API"
    And I validate the response contains "description" equals "Story created by Cucumber API framework"
    And I validate the response contains "story_type" equals "bug"
    And I validate the response contains "current_state" equals "started"
    And I validate the response contains "estimate" equals "3"
    And I send a DELETE request to "/projects/{P01.id}"

  Scenario: DELETE Story
    Given I send a "POST" request to "/projects" with json body
    """
    {
    "name": "Project created by API framework"
    }
    """
    And I save the response as "P02"
    When I send a DELETE request to "/projects/{P02.id}"
    Then I send a "GET" request to "/projects/{P02.id}"
    And I validate the response has status code 404