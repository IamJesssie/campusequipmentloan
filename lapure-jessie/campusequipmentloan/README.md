# Campus Equipment Loan System

A Spring Boot application for managing equipment loans in a campus setting.

## System Overview

This system manages equipment loans for campus students, tracking equipment availability, student loans, and return processes with penalties for late returns.

### Domain Model

![Domain Model](https://www.plantuml.com/plantuml/png/RP31QiCm38RlUGgVG-W9tRWbJxGzxQGEPGU7EBjQaGq7Ue_qWUO84eLIMf4JVs_ZxsJNciaSuGgH1jgQegLmKY2pKARc0pXEJRr7OUiPbTH0CWlT-FvJQ4XbqcQSOzbUW_-3w6_dw4YHkGjvZYiqqtTEH2w7j_VXq4cFDSOV2s7g1J6ZF9k5O3kd1dSjrRPvWkrzXDqrHKCiWSTwCZRnEv-ZVv8JtNhZNgIXuPvO-e7R6LLPI-zIRHzwmNiYc0a6D__BpG4P3gswuuCrPbzPT2lJ2MrKOvuyjEKs3nh7Ym5vwHHb82_PJAuWvSWr)

### SOLID Principles Applied

1. **Single Responsibility Principle (SRP)**
   - Each class has a single responsibility:
     - `Equipment` manages equipment information
     - `Student` manages student information
     - `Loan` manages loan information
     - `LoanService` handles loan business logic
     - `PenaltyCalculator` handles penalty calculations

2. **Open/Closed Principle (OCP)**
   - The system is open for extension but closed for modification
   - `PenaltyCalculator` interface allows for different penalty calculation strategies

3. **Liskov Substitution Principle (LSP)**
   - `DailyPenaltyCalculator` can be substituted for any `PenaltyCalculator` implementation

4. **Interface Segregation Principle (ISP)**
   - Interfaces are kept focused and minimal
   - `PenaltyCalculator` has only the methods needed for penalty calculation

5. **Dependency Inversion Principle (DIP)**
   - High-level modules (`LoanService`) depend on abstractions (`PenaltyCalculator`)
   - Low-level details (`DailyPenaltyCalculator`) implement abstractions

## Business Rules

1. Maximum of 2 active loans per student
2. Loan length is 7 days
3. Loans are marked overdue if past the due date
4. Late returns incur a ₱50/day penalty

## API Endpoints

- **POST /api/loans** - Create a new loan
- **POST /api/loans/{id}/return** - Return a loan
- **GET /api/equipment/available** - List available equipment

## Technical Stack

- Java 17
- Spring Boot 3.x
- Spring Web, JPA, Validation
- H2 Database
- Maven
- JUnit 5, Mockito for testing
- OpenAPI/Swagger for API documentation

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven

### Running the Application

```bash
mvn spring-boot:run
```

### Running Tests

```bash
mvn test
```

### Accessing the API Documentation

After starting the application, access the Swagger UI at:
```
http://localhost:8080/swagger-ui.html
```

## Project Structure

- **model**: Domain entities
- **repository**: Data access interfaces
- **service**: Business logic
- **controller**: REST API endpoints
- **dto**: Data Transfer Objects
- **config**: Application configuration

## Strategy Pattern Implementation

The penalty calculation is implemented using the Strategy Pattern:

- `PenaltyCalculator` interface defines the contract for penalty calculation
- `DailyPenaltyCalculator` provides the implementation for daily rate penalties
- This allows for easy extension with different penalty calculation strategies in the future
