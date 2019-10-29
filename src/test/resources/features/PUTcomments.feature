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
  Scenario: PUT Comment
    When I send a "PUT" request to "/projects/{Project.id}/stories/{Story.id}/comments/{Comment.id}" with json body
    """
    {
    "text": "Comment changed by cucumber"
    }
    """
    Then I validate the response has status code 200
    And I validate the response contains "text" equals "Comment changed by cucumber"
    And I validate the response contains "kind" equals "comment"
    And I send a DELETE request to "/projects/{Project.id}/stories/{Story.id}"
    And I validate the response has status code 204
    And I send a DELETE request to "/projects/{Project.id}"
    And I validate the response has status code 204

  @comment
  Scenario: PUT Comment with data from data table
    When I send a "PUT" request to "/projects/{Project.id}/stories/{Story.id}/comments/{Comment.id}"
      | text              | Comment changed by data table   |
    Then I validate the response has status code 200
    And I validate the response contains "text" equals "Comment changed by data table"
    And I validate the response contains "kind" equals "comment"
    And I send a DELETE request to "/projects/{Project.id}/stories/{Story.id}"
    And I validate the response has status code 204
    And I send a DELETE request to "/projects/{Project.id}"
    And I validate the response has status code 204

  @comment
  Scenario: PUT Comment with data from a json file
    When I send a "PUT" request to "/projects/{Project.id}/stories/{Story.id}/comments/{Comment.id}" with json file "json/requestPutComment.json"
    Then I validate the response has status code 200
    And I validate the response contains "text" equals "Comment changed from a json file"
    And I validate the response contains "kind" equals "comment"
    And I send a DELETE request to "/projects/{Project.id}/stories/{Story.id}"
    And I validate the response has status code 204
    And I send a DELETE request to "/projects/{Project.id}"
    And I validate the response has status code 204
