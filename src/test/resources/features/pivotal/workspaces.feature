Feature: Workspaces

  Background:
    Given I use the "pivotal" service and the "owner" account
    And I send a "POST" request to "/projects" with json body
    """
    {
    "name": "Project created by cucumber",
    "new_account_name": "New Account"
    }
    """
    And I save the response as "P"
    And I save the request endpoint for deleting

  @cleanData
  Scenario: POST Workspace
    When I send a "POST" request to "/my/workspaces" with json body
    """
    {
    "name": "Workspace",
    "project_ids": [(P.id)]
    }
    """
    And I save the request endpoint for deleting
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Workspace"
