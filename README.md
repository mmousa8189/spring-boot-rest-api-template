# Spring Boot REST API Template

A reusable template for building scalable **REST APIs with Spring Boot**.  
This project provides a **clean, layered architecture** that follows best practices for structuring enterprise-grade backend services.

---

## 🚀 Features
- Standardized **Spring Boot project structure**
- Clear **separation of concerns** using layered architecture
- Ready-to-use **DTOs, Mappers, and Exception Handling**
- Configurable **application setup**
- **Entity auditing** with Hibernate Envers
- **API audit logging** with MongoDB (tracks all API requests and responses)
- **Spring Security** integration
- **Bean validation** for request data
- Development and production profiles
- Easily extendable for any backend service

---

## 📂 Project Structure

```
eg.com.company.projectname
│
├── controller       # Handles HTTP requests & responses (@RestController)
├── service          # Business logic & workflows (@Service)
├── repository       # Data persistence & CRUD operations (@Repository)
├── model
│   ├── entity       # JPA entities mapped to DB tables
│   └── dto          # Data Transfer Objects (API contracts)
├── mapper           # Converts Entities <-> DTOs (MapStruct / manual)
├── config           # Application-wide configurations (@Configuration)
├── exception        # Custom exceptions & global exception handler
├── utilities        # Helper classes for common reusable functions
└── Application.java # Main Spring Boot entry point
```

---

## 🏗️ Layered Architecture

### 1. **Controller Layer**
- Receives HTTP requests, validates input, and delegates to the Service layer.  
- Annotated with `@RestController`.  
- **Rule:** No business logic here, only request/response handling.  

### 2. **Service Layer**
- Core **business logic and rules** live here.  
- Annotated with `@Service` and often `@Transactional`.  
- Orchestrates repository calls and integrates with external services.  

### 3. **Repository Layer**
- Responsible for **data persistence**.  
- Annotated with `@Repository`, typically extends `JpaRepository` or `CrudRepository`.  

### 4. **Model Layer**
- **Entity:** Database-mapped domain objects annotated with `@Entity` and `@Audited`.  
- **DTO:** Data Transfer Objects with validation annotations to safely expose only required data.  

### 5. **Mapper Layer**
- Converts between Entities and DTOs.  
- Can use **MapStruct** or **manual mapping**.  

### 6. **Configuration Layer**
- Beans, security, and application-wide setup (`@Configuration`).  

### 7. **Utilities Layer**
- Stateless helper classes (e.g., date handling, string formatting, validation).  

### 8. **Exceptions Layer**
- Centralized error handling with `@ControllerAdvice`.  
- Custom exceptions (e.g., `UserNotFoundException`).  
- Provides consistent error responses with proper HTTP status codes.  
- Validation error handling for request data.  

---

## 🛠️ Tech Stack
- **Java 23**  
- **Spring Boot 3.5.5**  
- **Spring Data JPA**  
- **Spring Security**  
- **PostgreSQL** (database)  
- **MapStruct 1.5.5.Final** (for mapping Entities ↔ DTOs)  
- **Lombok 1.18.30** (for boilerplate reduction)  
- **Hibernate Envers** (for entity auditing)  
- **Maven** (build tool)  

---

## ⚡ Getting Started

### Prerequisites
- Java 23  
- Maven 3.9+  
- PostgreSQL database  
- GitHub Desktop or Git CLI  

### Clone the Repository
```bash
git clone https://github.com/your-username/spring-boot-rest-api-template.git
cd spring-boot-rest-api-template
```

### Run the Application

#### Configure Database
Create a PostgreSQL database named `restapi` (or update the application.properties file with your database name).

#### Run with default profile
```bash
mvn spring-boot:run
```

#### Run with development profile (includes sample data)
```bash
mvn spring-boot:run -Dspring.profiles.active=dev
```

The API will be available at:  
👉 `http://localhost:8080/api/v1`

---

## 📌 Example API Endpoints

### API Endpoints

- `GET /api/v1/users` → Get all users  
- `POST /api/v1/users` → Create a user  
- `GET /api/v1/users/{id}` → Get user by ID  
- `PUT /api/v1/users/{id}` → Update user  
- `DELETE /api/v1/users/{id}` → Delete user  

### Audit Logging API Endpoints

- `GET /api/v1/audit-logs` → Get all audit logs (paginated)
- `GET /api/v1/audit-logs/{id}` → Get audit log by ID
- `GET /api/v1/audit-logs/action/{action}` → Get audit logs by action
- `GET /api/v1/audit-logs/endpoint/{endpoint}` → Get audit logs by endpoint
- `GET /api/v1/audit-logs/method/{method}` → Get audit logs by HTTP method
- `GET /api/v1/audit-logs/status/{status}` → Get audit logs by status
- `GET /api/v1/audit-logs/date-range` → Get audit logs by date range
- `GET /api/v1/audit-logs/stats` → Get audit statistics

*(You can adjust these according to your project needs.)*

---

## 📊 MongoDB API Audit Logging

This project includes a comprehensive API audit logging system using MongoDB to track all API requests and responses.

### Features

- Automatic logging of API requests and responses
- Captures HTTP method, endpoint, client IP, request/response payloads
- Tracks success/failure status of each request
- Records authenticated user who made the request
- Timestamps for all audit events
- Custom annotation `@AuditableApi` to mark methods for audit logging

### How to Use

1. **Add the annotation to your controller methods:**

```java
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @GetMapping
    @AuditableApi(action = "get_all_users")  // Define a descriptive action name
    public ResponseEntity<List<UserDto>> getAllUsers() {
        // Your controller logic
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/{id}")
    @AuditableApi(action = "get_user_by_id")  // Action for retrieving a specific user
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        // Your controller logic
        return ResponseEntity.ok(user);
    }
    
    @PostMapping
    @AuditableApi(action = "create_user")  // Action for user creation
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        // Your controller logic
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
    
    @PutMapping("/{id}")
    @AuditableApi(action = "update_user")  // Action for user update
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
        // Your controller logic
        return ResponseEntity.ok(updatedUser);
    }
    
    @DeleteMapping("/{id}")
    @AuditableApi(action = "delete_user")  // Action for user deletion
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        // Your controller logic
        return ResponseEntity.noContent().build();
    }
}
```

2. **Access audit logs via the API endpoints:**

```http
GET /api/v1/audit-logs            # Get all logs (paginated)
GET /api/v1/audit-logs/stats      # Get usage statistics
```

3. **Filter audit logs:**

```http
GET /api/v1/audit-logs/action/create_user    # Filter by action
GET /api/v1/audit-logs/status/FAILED         # View only failed requests
```

4. **Best practices for `@AuditableApi` annotation:**

- **Use consistent action naming conventions**: Prefer lowercase with underscores (e.g., `get_user`, `create_order`)
- **Be descriptive but concise**: Action names should clearly indicate what operation is being performed
- **Group related actions**: Use prefixes for related operations (e.g., `user_create`, `user_update`, `user_delete`)
- **Apply to all sensitive operations**: Especially those involving data creation, modification, or deletion
- **Consider security implications**: Ensure sensitive data is properly masked in request/response payloads

### Implementation Details

- Uses Spring AOP for non-intrusive request/response interception
- MongoDB for scalable, schema-flexible storage
- Asynchronous logging to minimize performance impact
- Configurable via application.properties

---

## 🔒 Optimistic Concurrency Control

This project implements Optimistic Concurrency Control (OCC) to handle concurrent updates to the same database record without locking it.

### What is Optimistic Concurrency?

Optimistic Concurrency Control (OCC) is a strategy used to handle concurrent updates to the same database record without locking it.
- **The assumption**: conflicts are rare.
- Each transaction works on its own copy of data, and before committing, it checks if the data has been modified by someone else.
- If another transaction modified it → a conflict occurs → an exception (e.g., `OptimisticLockException`) is raised, and you decide how to resolve it (retry, reject, merge, etc.).

### Implementation in this Template

This template implements optimistic locking using Hibernate's `@Version` annotation:

```java
@MappedSuperclass
public abstract class AuditableEntity {
    // Other fields...
    
    @Version // 🔑 Concurrency check attribute
    @Column(name = "version", nullable = false)
    private Long version; // Hibernate handles this automatically
}
```

### How it Works

1. **Version Tracking**: Each entity has a version field that Hibernate automatically increments on each update.
2. **Conflict Detection**: When updating an entity, Hibernate checks if the version in the database matches the version when the entity was loaded.
3. **Exception Handling**: If versions don't match (meaning someone else updated the record), an `OptimisticLockException` is thrown.

### Benefits

- **No Database Locks**: Improves performance by avoiding database locks
- **Better Scalability**: Allows more concurrent operations
- **Conflict Resolution**: Provides clear mechanism for handling conflicts
- **Data Integrity**: Prevents the "lost update" problem where one user's changes overwrite another's

### Best Practices

- Handle `OptimisticLockException` gracefully in your service layer
- Consider implementing retry mechanisms for non-critical operations
- Provide clear feedback to users when conflicts occur
- Use optimistic locking for entities that are frequently read but rarely updated concurrently

---

## 📜 License
This project is licensed under the **MIT License** – see the [LICENSE](LICENSE) file for details.

---

## 🤝 Contributing
Contributions are welcome! Please fork this repo and submit a pull request.  
