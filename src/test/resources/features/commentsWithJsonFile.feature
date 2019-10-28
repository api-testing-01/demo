Feature: Stories


  Scenario: POST Story
    Given I send a "POST" request to "/projects" with json file "json/requestBodyProject.json"
    And I save the response as "P"
    And I send a "POST" request to "/projects/{P.id}/stories" with json file "json/requestBodyStory.json"
    And I save the response as "S"
    When I send a "POST" request to "/projects/{P.id}/stories/{S.id}/comments" with json file "json/requestBodyComments.json"
    And I save the response as "C"
    Then I validate the response has status code 200
    And I validate the response contains "text" equals "commentTest01"
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204

  Scenario: GET Story
    Given I send a "POST" request to "/projects" with json file "json/requestBodyProject.json"
    And I save the response as "P"
    And I send a "POST" request to "/projects/{P.id}/stories" with json file "json/requestBodyStory.json"
    And I save the response as "S"
    And I send a "POST" request to "/projects/{P.id}/stories/{S.id}/comments" with json file "json/requestBodyComments.json"
    And I save the response as "C"
    When I send a GET request to "/projects/{P.id}/stories/{S.id}/comments/{C.id}"
    And I save the response as "Z"
    Then I validate the response has status code 200
    And I validate the response contains "text" equals "commentTest01"
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204
