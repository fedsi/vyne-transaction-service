Feature: Create a new transaction

  Scenario: client makes call to create a new transaction POST /transactions
    Given we have a valid Transaction object
    When the client post the transaction object
    Then the response status code is 201
    And the response doesn't contain the custom header

  Scenario Outline: client makes call to retrieve a single transaction using API version
    When the client request a transaction by id using API version <apiVersion>
    Then the response status code is 200
    And the response contains the custom header
    And the response transaction field are "<date>", "<status>", <amount>, "<currency>", "<description>"
    Examples:
      | apiVersion | date        | status      | amount | currency | description                   |
      | 1          | 2021-12-05  | authorised  | 100    | GBP      | description_from API Version1 |
      | 2          | 2021-12-05  | authorised  | 100    | GBP      | description_from API Version2 |
