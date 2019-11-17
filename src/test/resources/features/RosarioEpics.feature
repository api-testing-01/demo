# new feature
# Tags: optional

Feature: Epics

  Background: Create project
    Given I send a "POST" request to "/projects" with json file "json/requestBodyProject.json"
    And I save the response as "proj"
    And I send a "PUT" request to "/projects/{proj.id}" with json body
    """
    {
    "name": "RPFH project4"
    }
    """
    And I validate the response has status code 200
    And I validate the response contains "name" equals "RPFH project4"

  Scenario: Working with epics feature
    When I send a "POST" request to "/projects/{proj.id}/epics" with json file "json/bodyCreateEpic.json"
    And I save the response as "epi"
    And I validate the response has status code 200
    And I validate the response contains "id" is not null
    And I validate the response contains "name" equals "My First Epic RPFH"
    And I send a "PUT" request to "/projects/{proj.id}/epics/{epi.id}" with json file "json/bodyEditEpic.json"
    And I validate the response has status code 200
    And I validate the response contains "name" equals "Edited by RPFH"
    And I validate the response contains "description" equals "Adding a description for this epic"
    And I send a GET request to "/projects/{proj.id}/epics/{epi.id}"
    And I validate the response contains "id" is not null
    Then I send a DELETE request to "/projects/{proj.id}/epics/{epi.id}"
    And I validate the response has status code 204
    And I send a DELETE request to "/projects/{proj.id}"
    And I validate the response has status code 204