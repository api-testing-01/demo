Feature: Comments

  Scenario: PUT comment
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
    And I send a "POST" request to "/projects/{P.id}/stories/{S.id}/comments" with json body
    """
    {
    "text": "Comment created by cucumber"
    }
    """
    And I save the response as "C"
    When I send a "PUT" request to "/projects/{P.id}/stories/{S.id}/comments/{C.id}" with json body
    """
    {
    "text": "Comment updated by cucumber"
    }
    """
    Then I validate the response has status code 200
    And I validate the response contains "text" equals "Comment updated by cucumber"
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204

  Scenario: GET comment
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
    And I send a "POST" request to "/projects/{P.id}/stories/{S.id}/comments" with json body
    """
    {
    "text": "Comment created by Cucumber"
    }
    """
    And I save the response as "C"
    When I send a GET request to "/projects/{P.id}/stories/{S.id}/comments/{C.id}"
    Then I validate the response contains "text" equals "Comment created by Cucumber"

  Scenario: POST comment
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
    When I send a "POST" request to "/projects/{P.id}/stories/{S.id}/comments" with json body
    """
    {
    "text": "Comment created by Cucumber"
    }
    """
    And I save the response as "C"
    Then I validate the response contains "text" equals "Comment created by Cucumber"

  Scenario: DELETE comment
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
    And I send a "POST" request to "/projects/{P.id}/stories/{S.id}/comments" with json body
    """
    {
    "text": "Comment created by Cucumber"
    }
    """
    And I save the response as "C"
    When I send a DELETE request to "/projects/{P.id}/stories{S.id}/comments/{C.id}"
    Then I validate the response has status code 204
    And I send a GET request to "/projects/{P.id}/stories/{S.id}/comments/{C.id}"
    And I validate the response contains "code" equals "unauthorized_operation"
    And I validate the response contains "kind" equals "error"
    And I validate the response contains "error" equals "Authorization failure."
