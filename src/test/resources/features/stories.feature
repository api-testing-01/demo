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
    "name": "Story created by cucumber",
    "labels": ["cucumber label"]
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
    And I validate the response contains "kind" equals "story"
    And I validate the response contains "story_type" equals "feature"
    And I validate the component 7 from "labels" equals "label"
    And I validate the component 9 from "labels" equals "cucumber label"
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204
