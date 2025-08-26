# Spring Boot REST API Template

A reusable template for building scalable **REST APIs with Spring Boot**.  
This project provides a **clean, layered architecture** that follows best practices for structuring enterprise-grade backend services.

---

## 🚀 Features
- Standardized **Spring Boot project structure**
- Clear **separation of concerns** using layered architecture
- Ready-to-use **DTOs, Mappers, and Exception Handling**
- Configurable **application setup**
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
- **Entity:** Database-mapped domain objects annotated with `@Entity`.  
- **DTO:** Data Transfer Objects to safely expose only required data.  

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

---

## 🛠️ Tech Stack
- **Java 21**  
- **Spring Boot 3.x**  
- **Spring Data JPA** (or MongoDB if needed)  
- **MapStruct** (for mapping Entities ↔ DTOs)  
- **Lombok** (for boilerplate reduction)  
- **Maven / Gradle** (build tool)  

---

## ⚡ Getting Started

### Prerequisites
- Java 21  
- Maven 3.9+ (or Gradle if configured)  
- GitHub Desktop or Git CLI  

### Clone the Repository
```bash
git clone https://github.com/your-username/spring-boot-rest-api-template.git
cd spring-boot-rest-api-template
```

### Run the Application
```bash
mvn spring-boot:run
```
The API will be available at:  
👉 `http://localhost:8080`

---

## 📌 Example API Endpoints
- `GET /api/v1/users` → Get all users  
- `POST /api/v1/users` → Create a user  
- `GET /api/v1/users/{id}` → Get user by ID  
- `PUT /api/v1/users/{id}` → Update user  
- `DELETE /api/v1/users/{id}` → Delete user  

*(You can adjust these according to your project needs.)*

---

## 📜 License
This project is licensed under the **MIT License** – see the [LICENSE](LICENSE) file for details.

---

## 🤝 Contributing
Contributions are welcome! Please fork this repo and submit a pull request.  
