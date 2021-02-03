Feature: Projects

  @cleanData
  Scenario: POST Project
    Given I use the "pivotal" service and the "owner" account
    When I send a POST request to "/projects" with json body
    """
    {
    "name": "Project updated by cucumber",
    "public": true,
    "new_account_name": "New Account"
    }
    """
    And I save the request endpoint for deleting
    Then I validate the response has status code 200
    And I validate the response contains:
      | kind             | project                     |
      | name             | Project updated by cucumber |
      | iteration_length | 1                           |
      | week_start_day   | Monday                      |
      | point_scale      | 0,1,2,3                     |
      | enable_tasks     | true                        |
      | public           | true                        |
      | project_type     | public                      |
