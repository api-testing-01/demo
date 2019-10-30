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

  Scenario: GET Story
    Given I send a "POST" request to "/projects" with json body
    """
    {
    "name": "Project created by Cucumber"
    }
    """
    And I save the response as "P"
    And I send a "POST" request to "/projects/{P.id}/stories" with json body
    """
    {
    "name": "Story created by Cucumber"
    }
    """
    And I save the response as "S"
    When I send a GET request to "/projects/{P.id}/stories/{S.id}"
    Then I validate the response contains "name" equals "Story created by Cucumber"

  Scenario: POST Story
    Given I send a "POST" request to "/projects" with json body
    """
    {
    "name": "Project created by Cucumber"
    }
    """
    And I save the response as "P"
    When I send a "POST" request to "/projects/{P.id}/stories" with json body
    """
    {
    "name": "Story created by Cucumber"
    }
    """
    And I save the response as "S"
    Then I validate the response contains "name" equals "Story created by Cucumber"

  Scenario: DELETE Story
    Given I send a "POST" request to "/projects" with json body
    """
    {
    "name": "Project created by Cucumber"
    }
    """
    And I save the response as "P"
    And I send a "POST" request to "/projects/{P.id}/stories" with json body
    """
    {
    "name": "Story created by Cucumber"
    }
    """
    And I save the response as "S"
    When I send a DELETE request to "/projects/{P.id}/stories{S.id}"
    Then I validate the response has status code 204
    And I send a GET request to "/projects/{P.id}/stories/{S.id}"
    And I validate the response contains "code" equals "unauthorized_operation"
    And I validate the response contains "kind" equals "error"
    And I validate the response contains "error" equals "Authorization failure."






