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
- **API audit logging** with MongoDB
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

## 📜 License
This project is licensed under the **MIT License** – see the [LICENSE](LICENSE) file for details.

---

## 🤝 Contributing
Contributions are welcome! Please fork this repo and submit a pull request.  
