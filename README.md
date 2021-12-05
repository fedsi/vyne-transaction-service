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
This project uses API versioning only on _find/{{transactionId}}_ endpoint.

The two different versions can be called using the following Media Types:
 
- Version 1: 
```
application/vyne.transaction.service.v1+json
```
- Version 2:
```
  application/vyne.transaction.service.v2+json
```
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

The offer must be active (expiration date > now)

_localhost:8080/api/transactions/{{transactionId}}_
