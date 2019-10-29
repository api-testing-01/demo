Feature: Stories

  Background:
    Given I send a "POST" request to "/projects" with json body
    """
    {
    "name": "Project Automation"
    }
    """
    And I save the response as "Project"

  @story
  Scenario: POST Story with only name
    When I send a "POST" request to "/projects/{Project.id}/stories" with json body
    """
    {
    "name": "Story Automation"
    }
    """
    And I save the response as "Story"
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Story Automation"
    And I send a DELETE request to "/projects/{Project.id}"
    And I validate the response has status code 204

  @story
  Scenario: POST Story with custom data
    When I send a "POST" request to "/projects/{Project.id}/stories"
      | name              | Story Automation               |
      | description       | story created from data table  |
      | story_type        | bug                            |
      | current_state     | accepted                       |
    And I save the response as "Story"
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Story Automation"
    And I validate the response contains "story_type" equals "bug"
    And I validate the response contains "current_state" equals "accepted"
    And I validate the response contains "description" equals "story created from data table"
    And I send a DELETE request to "/projects/{Project.id}"
    And I validate the response has status code 204

  @story
  Scenario: POST Story with data from json file
    When I send a "POST" request to "/projects/{Project.id}/stories" with json file "json/requestPostStory.json"
    And I save the response as "Story"
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Story Automation"
    And I validate the response contains "story_type" equals "bug"
    And I validate the response contains "current_state" equals "accepted"
    And I validate the response contains "description" equals "story created from a json file"
    And I send a DELETE request to "/projects/{Project.id}"
    And I validate the response has status code 204