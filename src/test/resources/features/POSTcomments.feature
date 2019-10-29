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

  @comment
  Scenario: POST Comment
    When I send a "POST" request to "/projects/{Project.id}/stories/{Story.id}/comments" with json body
    """
    {
    "text": "Comment created by cucumber"
    }
    """
    And I save the response as "Comment"
    Then I validate the response has status code 200
    And I validate the response contains "text" equals "Comment created by cucumber"
    And I validate the response contains "kind" equals "comment"
    And I send a DELETE request to "/projects/{Project.id}/stories/{Story.id}"
    And I validate the response has status code 204
    And I send a DELETE request to "/projects/{Project.id}"
    And I validate the response has status code 204

  @comment
  Scenario: POST Comment with data from data table
    When I send a "POST" request to "/projects/{Project.id}/stories/{Story.id}/comments"
      | text              | Comment created by data table   |
    And I save the response as "Comment"
    Then I validate the response has status code 200
    And I validate the response contains "text" equals "Comment created by data table"
    And I validate the response contains "kind" equals "comment"
    And I send a DELETE request to "/projects/{Project.id}/stories/{Story.id}"
    And I validate the response has status code 204
    And I send a DELETE request to "/projects/{Project.id}"
    And I validate the response has status code 204

  @comment
  Scenario: POST Comment with data from a json file
    When I send a "POST" request to "/projects/{Project.id}/stories/{Story.id}/comments" with json file "json/requestPostComment.json"
    And I save the response as "Comment"
    Then I validate the response has status code 200
    And I validate the response contains "text" equals "Comment created from a json file"
    And I validate the response contains "kind" equals "comment"
    And I send a DELETE request to "/projects/{Project.id}/stories/{Story.id}"
    And I validate the response has status code 204
    And I send a DELETE request to "/projects/{Project.id}"
    And I validate the response has status code 204
