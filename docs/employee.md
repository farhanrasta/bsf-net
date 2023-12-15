# Employee API Spec

## Create Employee

Endpoint : POST /api/employees

Request Header :

- X-API-TOKEN : Token (Mandatory)

Request Body :

```json
{
  "fullname" : "Satria Abimayu",
  "email" : "satab@bankbsf.com",
  "salary" : 5000000,
  "status" : "average"
}
```

Response Body (Success) :

```json
{
  "data" : {
    "id" : "random-string",
    "fullname" : "Satria Abimayu",
    "email" : "satab@bankbsf.com",
    "salary" : 5000000,
    "status" : "average"
  }
}
```

Response Body (Failed) :

```json
{
  "errors" : "invalid email"
}
```

## Update Employee

Endpoint : PUT /api/employees/{employeeId}

Request Header :

- X-API-TOKEN : Token (Mandatory)

Request Body :

```json
{
  "fullname" : "",
  "email" : "satab@bankbsf.com",
  "salary" : 5000000,
  "status" : "average"
}
```

Response Body (Success) :

```json
{
  "data" : {
    "id" : "random-string",
    "fullname" : "Satria Abimayu",
    "email" : "satab@bankbsf.com",
    "salary" : 5000000,
    "status" : "average"
  }
}
```

Response Body (Failed) :

## Get Employee

Endpoint : GET /api/employees/{employeeId}

Request Header :

- X-API-TOKEN : Token (Mandatory)

Response Body (Success) : 

```json
{
  "data" : {
    "id" : "random-string",
    "fullname" : "Satria Abimayu",
    "email" : "satab@bankbsf.com",
    "salary" : 5000000,
    "status" : "average"
  }
}
```

Response Body (Failed) :

```json
{
  "error" : "employee is not found"
}
```

## Search Employee

Endpoint : GET /api/employees

Query Param :

- name : String, employee name, using like query, optional
- email : String, employee email, using like query, optional
- salary : String, employee salary, using like query, optional
- status : String, employee statuw, using like query, optional
- page : Integer, start from 0, default 0
- size : Integer, default 0

Request Header :

- X-API-TOKEN : Token (Mandatory)

Response Body (Success) : 

```json
{
  "data" : [
    {
      "id" : "random-string",
      "fullname" : "Satria Abimayu",
      "email" : "satab@bankbsf.com",
      "salary" : 5000000,
      "status" : "average"
    }
  ],
  "paging" : {
    "currentpage" : "0",
    "totalpage" : "10",
    "size" : "10"
  }
}
```

Response Body (Failed) :

```json
{
  "errors" : "Unauthorized"
}
```

## Remove Employee

Endpoint : DELETE /api/employees/{idEmployee}

Request Header :

- X-API-TOKEN : Token (Mandatory)

Response Body (Success) : 

```json
{
  "data" : "OK"
}
```

Response Body (Failed) :

```json
{
  "error" : "Employee is not found"
}
```