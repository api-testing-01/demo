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
    "text": "Comment created by cucumberffffff"
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
    And I send a "DELETE" request to "/projects/{P.id}"
    And I validate the response has status code 204
