Feature: Stories

  Scenario: POST Story
    Given I send a "POST" request to "/projects" with json file "json/requestBodyProject.json"
    And I save the response as "P"
    When I send a "POST" request to "/projects/{P.id}/stories" with json file "json/requestBodyStory.json"
    And I save the response as "S"
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "SotoryTest0"
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204

  Scenario: PUT Story
    Given I send a "POST" request to "/projects" with json file "json/requestBodyProject.json"
    And I save the response as "P"
    And I send a "POST" request to "/projects/{P.id}/stories" with json file "json/requestBodyStory.json"
    And I save the response as "S"
    When I send a "PUT" request to "/projects/{P.id}/stories/{S.id}" with json file "json/putRequestBodyStory.json"
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Edgarstorytest00000"
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204

  Scenario: GET Story
    Given I send a "POST" request to "/projects" with json file "json/requestBodyProject.json"
    And I save the response as "P"
    And I send a "POST" request to "/projects/{P.id}/stories" with json file "json/requestBodyStory.json"
    And I save the response as "S"
    When I send a GET request to "/projects/{P.id}/stories"
    And I save the response as "Z"
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "[SotoryTest0]"
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204

  Scenario: DELETE Story
    Given I send a "POST" request to "/projects" with json file "json/requestBodyProject.json"
    And I save the response as "P"
    And I send a "POST" request to "/projects/{P.id}/stories" with json file "json/requestBodyStory.json"
    And I save the response as "S"
    And I validate the response has status code 200
    And I validate the response contains "name" equals "SotoryTest0"
    When I send a DELETE request to "/projects/{P.id}/stories/{S.id}"
    Then I validate the response has status code 204
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204