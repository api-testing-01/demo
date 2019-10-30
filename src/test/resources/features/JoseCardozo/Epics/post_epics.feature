Feature: Epic operations Post

  Background: Pre-conditions
    Given I send a "POST" request to "/projects"
      | name             | Project created by cucumber |
      | public           | true                        |
      | week_start_day   | Tuesday                     |
      | iteration_length | 2                           |
    And I save the response as "P"

  @PostEpics
  Scenario: Create a new epic (Post)
    When I send a "POST" request to "/projects/{P.id}/epics" with json body
    """
       {
         "description":"This is a test for post an epic",
         "label":
           {
            "name":"api testing"
           },
         "name":"Epic 1"
       }
    """
    Then I validate the response has status code 200
    And I validate the response contains "description" equals "This is a test for post an epic"
    And I validate the response contains "label.name" equals "api testing"
    And I validate the response contains "name" equals "Epic 1"
    And I send a DELETE request to "/projects/{P.id}"
    And I validate the response has status code 204
