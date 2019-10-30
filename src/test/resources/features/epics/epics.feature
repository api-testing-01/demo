Feature: Endpoint /projects/{project_id}/stories
  Background: Project is created
    Given I send a "POST" request to "/projects" with json file "json/requestBodyProjectPOST.json"
    And I save the response as "P"
    Then I validate the response has status code 200

  Scenario: POST /projects/{project_id}/epics
    When I send a "POST" request to "/projects/{P.id}/epics" with json file "json/request/bodyEpicsPOST.json"
    And I save the response as "E"
    Then I validate the response has status code 200
    And I validate the response contains the values equals to json file "json/response/bodyEpicsPOST.json"
    And I send a "DELETE" request with no body to "/projects/{P.id}"
    And I validate the response has status code 204

  Scenario: GET /projects/{project_id}/epics/{epic_id}
    When I send a "POST" request to "/projects/{P.id}/epics" with json file "json/request/bodyEpicsPOST.json"
    And I save the response as "E"
    And I validate the response has status code 200
    When I send a "GET" request with no body to "/projects/{P.id}/epics/{E.id}"
    Then I validate the response has status code 200
    And I validate the response contains the values equals to json file "json/response/bodyEpicsPOST.json"
    And I send a "DELETE" request with no body to "/projects/{P.id}"
    And I validate the response has status code 204

  Scenario: PUT /projects/{project_id}/epics/{epic_id}
    When I send a "POST" request to "/projects/{P.id}/epics" with json file "json/request/bodyEpicsPOST.json"
    And I save the response as "E"
    And I validate the response has status code 200
    When I send a "PUT" request to "/projects/{P.id}/epics/{E.id}" with json file "json/request/bodyEpicsPUT.json"
    Then I validate the response has status code 200
    And I validate the response contains the values equals to json file "json/response/bodyEpicsPUT.json"
    And I send a "DELETE" request with no body to "/projects/{P.id}"
    And I validate the response has status code 204

  Scenario: DELETE /projects/{project_id}/epics/{epic_id}
    When I send a "POST" request to "/projects/{P.id}/epics" with json file "json/request/bodyEpicsPOST.json"
    And I save the response as "E"
    And I validate the response has status code 200
    When I send a "DELETE" request with no body to "/projects/{P.id}/epics/{E.id}"
    Then I validate the response has status code 204
    And I send a "GET" request with no body to "/projects/{P.id}/epics/{E.id}"
    And I validate the response has status code 404
    And I send a "DELETE" request with no body to "/projects/{P.id}"
    And I validate the response has status code 204