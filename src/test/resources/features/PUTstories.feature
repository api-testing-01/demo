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
  Scenario: POST Story with only name
    When I send a "PUT" request to "/projects/{Project.id}/stories/{Story.id}" with json body
    """
    {
    "name": "Story Automation Updated"
    }
    """
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Story Automation Updated"
    When I send a DELETE request to "/projects/{Project.id}/stories/{Story.id}"
    Then I validate the response has status code 204
    And I send a DELETE request to "/projects/{Project.id}"
    And I validate the response has status code 204

  @story
  Scenario: POST Story with custom data
    When I send a "PUT" request to "/projects/{Project.id}/stories/{Story.id}"
      | name              | Story Automation Updated       |
      | description       | story updated from data table  |
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Story Automation Updated"
    And I validate the response contains "description" equals "story updated from data table"
    When I send a DELETE request to "/projects/{Project.id}/stories/{Story.id}"
    Then I validate the response has status code 204
    And I send a DELETE request to "/projects/{Project.id}"
    And I validate the response has status code 204

  @story
  Scenario: POST Story with data from json file
    When I send a "POST" request to "/projects/{Project.id}/stories" with json file "json/requestPutStory.json"
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Story Automation Updated"
    And I validate the response contains "story_type" equals "bug"
    And I validate the response contains "current_state" equals "accepted"
    And I validate the response contains "description" equals "story updated from a json file"
    When I send a DELETE request to "/projects/{Project.id}/stories/{Story.id}"
    Then I validate the response has status code 204
    And I send a DELETE request to "/projects/{Project.id}"
    And I validate the response has status code 204