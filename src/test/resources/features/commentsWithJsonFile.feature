Feature: Comments

  Background: Project and Story are created
    Given I send a "POST" request to "/projects" with json file "json/requestBodyProject.json"
    And I save the response as "P"
    And I send a "POST" request to "/projects/{P.id}/stories" with json file "json/requestBodyStory.json"
    And I save the response as "S"

  Scenario: POST Comment
    When I send a "POST" request to "/projects/{P.id}/stories/{S.id}/comments" with json file "json/requestBodyStoryComments.json"
    Then I validate the response has status code 200
    And I validate the response contains "text" equals "This is a comment added for Story created by cucumber JsonFile"
    And I send a "DELETE" request to "/projects/{P.id}"
    And I validate the response has status code 204

  Scenario: GET Comment
    When I send a "GET" request to "/projects/{P.id}/stories/{S.id}/comments"
    Then I validate the response has status code 200
    And I validate the response contains "text" equals "This is a comment added for Story created by cucumber JsonFile"
    And I send a "DELETE" request to "/projects/{P.id}"
    And I validate the response has status code 204