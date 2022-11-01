@api
Feature: Api automation test cases

  Background:
    Given set the base uri


  Scenario Outline: Verify that getting a random valid user id returns the user’s details
    Given retrieve all user details
    Then retrieve a random user details by user id "<userId>"
    Then the random user details are verify correct
    Examples:
      | userId |
      | 1      |
      | 5      |
      | 8      |


  Scenario: Verify that all posts have valid post ids (integer between 1-100) when getting posts for the same user.
    Given user set the post resources endpoint
    When user want to retrieve the posts details for using the user id "7"
    When user submit the "get" request
    Then the response is 200 success
    Then all of the post id verify correct


  Scenario: Verify that doing a post using the same user with a non-empty title and body will return a correct response
    Given user set the post resources endpoint
    And user set the request body using user id "2"
    When user submit the "post" request
    Then the response is 201 created
    Then the response data is verify correct


