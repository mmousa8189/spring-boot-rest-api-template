# API Documentation

This document provides details about the REST API endpoints available in this application.

## Base URL

All API endpoints are prefixed with: `/api/v1`

## Authentication

Currently, the API does not require authentication. This should be implemented before deploying to production.

## Endpoints

### User Management

#### Get All Users

```
GET /users
```

**Response:**
```json
[
  {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "createdAt": "2025-08-26T10:30:00",
    "updatedAt": "2025-08-26T10:30:00"
  },
  {
    "id": 2,
    "firstName": "Jane",
    "lastName": "Smith",
    "email": "jane.smith@example.com",
    "createdAt": "2025-08-26T10:35:00",
    "updatedAt": "2025-08-26T10:35:00"
  }
]
```

#### Get User by ID

```
GET /users/{id}
```

**Parameters:**
- `id` (path parameter): The ID of the user to retrieve

**Response:**
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "createdAt": "2025-08-26T10:30:00",
  "updatedAt": "2025-08-26T10:30:00"
}
```

#### Create User

```
POST /users
```

**Request Body:**
```json
{
  "firstName": "New",
  "lastName": "User",
  "email": "new.user@example.com",
  "password": "password123"
}
```

**Response:**
```json
{
  "id": 3,
  "firstName": "New",
  "lastName": "User",
  "email": "new.user@example.com",
  "createdAt": "2025-08-26T11:00:00",
  "updatedAt": "2025-08-26T11:00:00"
}
```

#### Update User

```
PUT /users/{id}
```

**Parameters:**
- `id` (path parameter): The ID of the user to update

**Request Body:**
```json
{
  "firstName": "Updated",
  "lastName": "User",
  "email": "updated.user@example.com",
  "password": "newpassword123"
}
```

**Response:**
```json
{
  "id": 1,
  "firstName": "Updated",
  "lastName": "User",
  "email": "updated.user@example.com",
  "createdAt": "2025-08-26T10:30:00",
  "updatedAt": "2025-08-26T11:15:00"
}
```

#### Delete User

```
DELETE /users/{id}
```

**Parameters:**
- `id` (path parameter): The ID of the user to delete

**Response:**
- Status: 204 No Content

### Health Check

#### Get Health Status

```
GET /health
```

**Response:**
```json
{
  "status": "UP",
  "timestamp": "2025-08-26T11:30:00"
}
```

## Error Responses

### Resource Not Found (404)

```json
{
  "timestamp": "2025-08-26T12:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "User not found with id: 99",
  "path": "/api/v1/users/99"
}
```

### Validation Error (400)

```json
{
  "timestamp": "2025-08-26T12:15:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "errors": {
    "email": "Email should be valid",
    "firstName": "First name is required"
  }
}
```

### Server Error (500)

```json
{
  "timestamp": "2025-08-26T12:30:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "An unexpected error occurred",
  "path": "/api/v1/users"
}
```
