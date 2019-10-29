Feature: Comments

  Background:
    Given I send a "POST" request to "/projects" with json body
    """
    {
    "name": "Project Automation"
    }
    """
    And I save the response as "Project"
    And I send a "POST" request to "/projects/{Project.id}/stories" with json body
    """
    {
    "name": "Story Automation"
    }
    """
    And I save the response as "Story"
    And I send a "POST" request to "/projects/{Project.id}/stories/{Story.id}/comments" with json body
    """
    {
    "text": "Comment created by cucumber"
    }
    """
    And I save the response as "Comment"

  @comment
  Scenario: DELETE comment
    When I send a DELETE request to "/projects/{Project.id}/stories/{Story.id}/comments/{Comment.id}"
    Then I validate the response has status code 204
    And I send a DELETE request to "/projects/{Project.id}/stories/{Story.id}"
    And I validate the response has status code 204
    And I send a DELETE request to "/projects/{Project.id}"
    And I validate the response has status code 204