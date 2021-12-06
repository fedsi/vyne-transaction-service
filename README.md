# PayVyne Transaction Service

A simple Java application REST which serves a REST API for an online merchant.

## API Reference
Complete API documentation can be build using the provided ``yaml/VyneTransactionService.yaml`` file.

## Prerequisites
```
- Java 1.8+
- GIT
- Gradle
```

## Authentication
The POST, PUT and DELETE endpoints are authenticated using Basic Auth.

GET endpoints are not authenticated.

## Versioning
This project uses API versioning only on _/transactions/find/{{transactionId}}_ endpoint.

The two different versions can be called using the following Media Types:
 
- Version 1: 
```
application/vyne.transaction.service.v1+json
```
- Version 2:
```
  application/vyne.transaction.service.v2+json
```
## Filter

The FindTransactionFilter will add a custom Header (_X-Custom-Header_) in the response to all GET endpoints.

This functionality is tested with Cucumber (feature file: _transaction-service/src/test/resources/transaction/transaction.feature_)

The first scenario check that the custom header is not included when we create a new transaction (POST)

```
Scenario: client makes call to create a new transaction POST /transactions
    Given we have a valid Transaction object
    When the client post the transaction object
    Then the response status code is 201
    And the response doesn't contain the custom header
```
The second one check that the custom header is included when we retrieve a transaction (GET) on both API version 1 and 2
```
Scenario Outline: client makes call to retrieve a single transaction using API version
    When the client request a transaction by id using API version <apiVersion>
    Then the response status code is 200
    And the response contains the custom header
    And the response transaction field are "<date>", "<status>", <amount>, "<currency>", "<description>"
    
    Examples:
    | apiVersion | date        | status      | amount | currency | description                   |
    | 1          | 2021-12-05  | authorised  | 100    | GBP      | description_from API Version1 |
    | 2          | 2021-12-05  | authorised  | 100    | GBP      | description_from API Version2 |
```

To execute these tests, run this command from the repository root:
```
./gradlew cucumberTests
```

The result is a summary of all the configured scenarios:
```
3 Scenarios (3 passed)
12 Steps (12 passed)
0m9,331s
```

The full Cucumber report can  be found in the build folder (_transaction-service/build/cucumber-report.html_)
## Running

Application entry point -  _TransactionServiceApplication_

**POST** - Create a new transaction

_http://localhost:8080/api/transactions_

```
{
    "date" : "2021-12-05",
    "status": "authorised",
    "amount": 100,
    "currency": "GBP"
}
```

**GET** - search for an existing transaction

_http://localhost:8080/api/transactions/find/{{transactionId}}_

Examples of returned JSON:

```
{
    "id": 1,
    "date": "2021-12-05T00:00:00.000+00:00",
    "status": "authorised",
    "amount": 100,
    "currency": "GBP",
    "description": "description_from API Version1"
}
```

**PUT** - Update an existing transaction

_localhost:8080/api/transactions/{transactionId}_

```
{
    "id": 1,
    "date": "2021-12-05T00:00:00.000+00:00",
    "status": "update",
    "amount": 10,
    "currency": "EUR",
    "description": "updated description"
}
```

**DELETE** - Delete an existing transaction

_localhost:8080/api/transactions/{{transactionId}}_
