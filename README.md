# Carbon Cell Assignment

## Introduction

This project is aimed at providing APIs for user authentication, data retrieval, Ethereum account balance retrieval, and
user management.

## APIs

### Authentication

#### Login User

- **URL:** `/api/v1/auth/login`
- **Method:** `POST`
- **Description:** Authenticate user and generate JWT token.
- **Request Body:**
  ```json
  {
    "email": "user@example.com",
    "password": "password"
  }
- **Response Body:**
  ```json
  {
    
  "httpStatus": 200,
  "success": true,
  "timestamp": "2024-04-01T00:00:00Z",
  "message": "User logged in successfully",
  "result": {
    "authToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
  },
  "error": null

  }
  ```

#### Logout User

- **Url:** `/api/v1/auth/logout`
- **Method:** `DELETE`
- **Description:** Invalidate JWT token and logout user.
- **Request Header:** `Authorization: Bearer {authToken}`

```json
{
    "httpStatus": 200,
    "success": true,
    "timestamp": "2024-04-01T00:00:00Z",
    "message": "User logged out successfully",
    "result": null,
    "error": null
}
```

### User Management

#### Get All Users
- **Url:** `/api/v1/users`
- **Method:** `GET`
- **Description:** Get all users.
- **Request Header:** `Authorization: Bearer {authToken}`
- **Response Body:**
  ```json
  {
    "httpStatus": 200,
    "success": true,
    "timestamp": "2024-04-01T00:00:00Z",
    "message": "Users retrieved successfully",
    "result": [
      {
        "id": "1",
        "firstName": "John",
        "lastName": "Doe",
        "phoneNumber": "1234567890",
        "email": "john.doe@example.com"
      },
      {
        "id": "2",
        "firstName": "Jane",
        "lastName": "Doe",
        "phoneNumber": "9876543210",
        "email": "jane.doe@example.com"
      }
    ],
    "error": null
  }
  ```
#### Get user by id
- **Url:** `/api/v1/users/{id}`
- **Method:** `GET`
- **Description:** Get user by id.
- **Request Header:** `Authorization: Bearer {authToken}`
- **Response Body:**
  ```json
  {
    "httpStatus": 200,
    "success": true,
    "timestamp": "2024-04-01T00:00:00Z",
    "message": "User retrieved successfully",
    "result": {
      "id": "1",
      "firstName": "John",
      "lastName": "Doe",
      "phoneNumber": "1234567890",
      "email": "john.doe@example.com"
    },
    "error": null
  }
  ```
#### Delete user by Id
- **Url:** `/api/v1/users/{id}`
- **Method:** `DELETE`
- **Description:** Delete user by id.
- **Request Header:** `Authorization: Bearer {authToken}`
- **Response Body:**
  ```json
  {
    "httpStatus": 200,
    "success": true,
    "timestamp": "2024-04-01T00:00:00Z",
    "message": "User deleted successfully",
    "result": null,
    "error": null
  }
  ```
#### Register user

- **Url:** `/api/v1/users/register`
- **Method:** `POST`
- **Description:** Register user.
- **Request Body:**
  ```json
  {
    "firstName": "John",
    "lastName": "Doe",
    "phoneNumber": "1234567890",
    "email": "john.doe@example.com",
    "password": "password123"
  }
  ```
- **Response Body:**
  ```json
  {
    "httpStatus": 200,
    "success": true,
    "timestamp": "2024-04-01T00:00:00Z",
    "message": "User registered successfully",
    "result": null,
    "error": null
  }
  ```

## Data Retrieval

#### Get data by category

- **Url:** `/api/v1/data-retrieval/category`
- **Method:** `GET`
- **Description:** Get data by category.
- **Request Header:** `Authorization: Bearer {authToken}`
- **Query Parameters:** `category`: Category of data to be retrieved (`optional`), `limit`: Limit of data to be retrieved (`optional`)
- **Response Body:**
  ```json
  {
    "httpStatus": 200,
    "success": true,
    "timestamp": "2024-04-01T00:00:00Z",
    "message": "Data retrieved successfully",
    "result": [
      {
        "id": "1",
        "name": "Data 1",
        "category": "Category A"
      }
    ]
    "error": null
  }
  ```

### Ethereum Account Balance

- **Url:** `/api/v1/ethereum-account-balance`
- **Method:** `GET`
- **Description:** Get Ethereum account balance.
- **Request Header:** `Authorization: Bearer {authToken}`
- **Query Parameters:** `address`: Address of the Ethereum account (`required`)
- **Response Body:**
  ```json
  {
    "httpStatus": 200,
    "success": true,
    "timestamp": "2024-04-01T00:00:00Z",
    "message": "Ethereum account balance retrieved successfully",
    "result": {
      "balance": 100
    },
    "error": null
  }
  ```

### Swagger UI
- The API documentation can be accessed using Swagger UI. Visit http://localhost:8080/swagger-ui/index.html after running the application to explore the available endpoints.

### JWT Token
- JWT (JSON Web Token) is required for authentication in all endpoints except user registration and login. Pass the JWT token in the Authorization header with the value Bearer <JWT_TOKEN> for authentication.

### Tech Stack
- Java
- Spring Boot
- Spring Security
- MySQL
- JWT
### Instructions
- Build the application with `mvn clean install`
- Run the application with `mvn spring-boot:run`
- Open http://localhost:8080/swagger-ui/index.html in your browser
- Enter the JWT token in the Authorization header
- Enter the required data in the request body
- Click on the submit button
- Check the response
###
Happy Coding!

