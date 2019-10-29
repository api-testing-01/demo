Feature: Stories

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

  @story
  Scenario: GET Story
    When I send a GET request to "/projects/{Project.id}/stories"
    Then I validate the response has status code 200
    When I send a DELETE request to "/projects/{Project.id}/stories/{Story.id}"
    Then I validate the response has status code 204
    And I send a DELETE request to "/projects/{Project.id}"
    And I validate the response has status code 204

  @story
  Scenario: GET Story by ID
    When I send a GET request to "/projects/{Project.id}/stories/{Story.id}"
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Story Automation"
    And I validate the response contains "story_type" equals "feature"
    And I validate the response contains "current_state" equals "unscheduled"
    When I send a DELETE request to "/projects/{Project.id}/stories/{Story.id}"
    Then I validate the response has status code 204
    And I send a DELETE request to "/projects/{Project.id}"
    And I validate the response has status code 204