Feature: Stories

  Background: Project and Story are created
    Given I send a "POST" request to "/projects" with json file "json/requestBodyProject.json"
    And I save the response as "P"
    And I send a "POST" request to "/projects/{P.id}/stories" with json file "json/requestBodyStory.json"
    And I save the response as "S"

  Scenario: PUT Story
    When I send a "PUT" request to "/projects/{P.id}/stories/{S.id}" with json body
    """
    {
    "name": "Story updated by cucumber with Json body"
    }
    """
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Story updated by cucumber with Json body"
    And I send a "DELETE" request to "/projects/{P.id}"
    And I validate the response has status code 204

  Scenario: GET Story
    When I send a "GET" request to "/projects/{P.id}/stories/{S.id}"
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Story created by cucumber JsonFile"
    And I send a "DELETE" request to "/projects/{P.id}"
    And I validate the response has status code 204

  Scenario: DELETE Story
    When I send a "DELETE" request to "/projects/{P.id}/stories/{S.id}"
    Then I validate the response has status code 204
    And I send a "DELETE" request to "/projects/{P.id}"
    And I validate the response has status code 204

