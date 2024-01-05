# User API Spec

## Register User

Endpoint : POST /api/users/register

Request Body :

```json 
{
    "username" : "farhanindrasta",
    "password" : "farhan1704",
    "name" : "Farhan Dani Indrasta"
}
```

Response Body (Success):

```json
{
    "data" : "Ok"
}
```

Response Body (Failed):

```json
{
    "errors" : "I Dont Know"
}
```

## Login User

Endpoint : POST /api/auth/login

Request Body :

```json 
{
    "username" : "farhanindrasta",
    "password" : "farhan1704"
}
```

Response Body (Success):

```json
{
    "data" : {
      "token" : "TOKEN",
      "expiredAt" : 164681687646 
    }
}
```

Response Body (Failed, 401):

```json
{
    "errors" : "username or password is incorrect"
}
```

## Profile User

Endpoint : GET /api/users/current

Request Header :

- X-API-TOKEN : Token (Mandatory)

Response Body (Success):

```json
{
  "data": {
    "id": 1,
    "firstname": "Farhan",
    "lastname": "Indrasta",
    "username": "farhanindrasta",
    "isUserToken": true,
    "token": "null",
    "expiredDate": "null"
  }
}
```

Response Body (Failed, 401):

```json
{
    "errors" : "Unauthorized"
}
```

## Update User

Endpoint : PATCH /api/users/current

Request Header :

- X-API-TOKEN : Token (Mandatory)

Request Body :

```json 
{
    "name" : "Rifqy Imam Ramadhan", 
    "password" : "rifqy0112"
}
```

Response Body (Success):

```json
{
    "data" : {
      "name" : "Rifqy Imam Ramadhan", 
      "password" : "rifqy0112"
    }
}
```

Response Body (Failed, 401):

```json
{
    "errors" : "Access denied"
}
```

## Logout User

Endpoint : GET /api/auth/logout

Request Header :

- X-API-TOKEN : Token (Mandatory)

Response Body (Success) :

```json
{
    "data" : "Ok"
}
```

