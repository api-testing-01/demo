Feature: Epics

  Scenario: POST Epic
    Given I send a "POST" request to "/projects" with json body
    """
    {
    "name": "Project with Epic created by cucumber"
    }
    """
    And I save the response as "Project"
    When I send a "POST" request to "/projects/{Project.id}/epics" with json body
    """
    {
    "name": "Epic1",
    "description":"This is the description for the Epic1",
    "label":{"name":"epic-label"}
    }
    """
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Epic1"
    And I validate the response contains "description" equals "This is the description for the Epic1"
    And I validate the response contains "label.name" equals "epic-label"
    And I send a DELETE request to "/projects/{Project.id}"
    And I validate the response has status code 204

  Scenario: PUT Epic
    Given I send a "POST" request to "/projects" with json body
    """
    {
    "name": "Project with Epic created by cucumber"
    }
    """
    And I save the response as "Project"
    And I send a "POST" request to "/projects/{Project.id}/epics" with json body
    """
    {
    "name": "Epic1",
    "description":"This is the description for the Epic1"
    }
    """
    And I save the response as "Epic"
    When I send a "PUT" request to "/projects/{Project.id}/epics/{Epic.id}" with json body
    """
    {
    "name": "Epic1_v2 updated by cucumber",
    "description":"New description for Epic1",
    "label":{"name":"added-epic-label"}
    }
    """
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Epic1_v2 updated by cucumber"
    And I validate the response contains "description" equals "New description for Epic1"
    And I validate the response contains "label.name" equals "added-epic-label"
    And I send a DELETE request to "/projects/{Project.id}"
    And I validate the response has status code 204

  Scenario: GET Epic
    Given I send a "POST" request to "/projects" with json body
    """
    {
    "name": "Project with Epic created by cucumber"
    }
    """
    And I save the response as "Project"
    And I send a "POST" request to "/projects/{Project.id}/epics" with json body
    """
    {
    "name": "Epic1",
    "description":"This is the description for the Epic1",
    "label":{"name":"epic-label"}
    }
    """
    And I save the response as "Epic"
    When I send a GET request to "/projects/{Project.id}/epics/{Epic.id}"
    Then I validate the response contains "name" equals "Epic1"
    And I validate the response contains "description" equals "This is the description for the Epic1"
    And I validate the response contains "label.name" equals "epic-label"
    And I validate the response contains "kind" equals "epic"
    And I send a DELETE request to "/projects/{Project.id}"
    And I validate the response has status code 204

  Scenario: DELETE Epic
    Given I send a "POST" request to "/projects" with json body
    """
    {
    "name": "Project with Epic created by cucumber"
    }
    """
    And I save the response as "Project"
    And I send a "POST" request to "/projects/{Project.id}/epics" with json body
    """
    {
    "name": "Epic1"
    }
    """
    And I save the response as "Epic"
    When I send a DELETE request to "/projects/{Project.id}/epics/{Epic.id}"
    Then I validate the response has status code 204
    And I send a GET request to "/projects/{Project.id}/epics/{Epic.id}"
    And I validate the response contains "code" equals "unfound_resource"
    And I validate the response contains "kind" equals "error"
    And I send a DELETE request to "/projects/{Project.id}"
    And I validate the response has status code 204