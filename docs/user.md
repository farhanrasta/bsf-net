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
      "expiredAt" : 164681687646 //  miliseconds
    }
}
```

Response Body (Failed, 401):

```json
{
    "errors" : "username or password is incorrect"
}
```

## Get User

Endpoint : GET /api/users/current

Request Header :

- X-API-TOKEN : Token (Mandatory)

Response Body (Success):

```json
{
    "data" : {
      "username" : "farhanindrasta",
      "name" : "Farhan Dani Indrasta"
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
    "name" : "Rifqy Imam Ramadhan", //  put if only want to update name
    "password" : "rifqy0112" //  put if only want to update password
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

Endpoint : DELETE /api/auth/logout

Request Header :

- X-API-TOKEN : Token (Mandatory)

Response Body (Success) :

```json
{
    "data" : "Ok"
}
```

