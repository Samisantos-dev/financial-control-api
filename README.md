# 💰 Financial Management System

A RESTful API developed with **Java and Spring Boot** to help users manage their personal finances by tracking monthly income and expenses.

The system allows users to register their income (money earned) and expenses (money spent), providing a structured way to organize and monitor their financial activities.

## 🚀 Technologies

- **Java** – Main programming language.
- **Spring Boot** – Backend application development.
- **Spring Data JPA** – Database persistence and management.
- **Hibernate** – Object-relational mapping (ORM).
- **PostgreSQL** – Relational database.
- **Maven** – Dependency management and project build.
- **REST API** – HTTP communication.

## ✨ Features

### 👤 User Management
- User registration.
- User authentication (login).
- User information management.
- Data validation.

### 💵 Income Management
- Register monthly income.
- Store the amount of money earned.
- Associate income records with their respective users.
- Organize financial income information.

### 💳 Expense Management
- Register expenses.
- Update existing expenses.
- Delete expenses.
- List expenses associated with a user.
- Organize expenses by financial category.

### 🗂️ Financial Categories
- Register and manage expense categories.
- Associate categories with expenses.
- Organize financial transactions.

## 🏗️ Project Architecture

The application follows a layered architecture, separating responsibilities to improve code organization, maintainability, and scalability.

| Layer | Responsibility |
|---|---|
| Controller | Handles HTTP requests and API endpoints. |
| Service | Implements business rules and application logic. |
| Repository | Manages database operations. |
| Model | Represents the application's entities. |
| DTO | Transfers data between the client and the application. |
| Mapper | Converts DTOs into entities and entities into DTOs. |
| Exception | Handles application errors and custom exceptions. |

## 🗄️ Database and Entity Relationships

The application uses **PostgreSQL** as its relational database and **JPA/Hibernate** for entity mapping and persistence.

Main entities:

- **User (Usuario):** Stores user information and account details.
- **Income (Receita):** Stores the user's monthly income.
- **Expense (Despesa):** Stores the user's financial expenses.
- **Category (Categoria):** Organizes expenses into different categories.

The entities are connected through JPA relationships, allowing financial records to be associated with their respective users and categories.

## 📦 Data Transfer Objects (DTOs)

The project uses DTOs to control the data exchanged between the client and the API.

- **Request DTOs:** Receive information sent by the client.
- **Response DTOs:** Return structured information without exposing sensitive data, such as user passwords.

The Mapper layer is responsible for converting DTOs into entities and entities into DTOs.

## ⚙️ How to Run the Project

### Prerequisites

- Java JDK
- Maven
- PostgreSQL
- Git

### 1. Clone the repository

```bash
git clone https://github.com/Samisantos-dev/SistemaDeControleFinanceiro.git
```

### 2. Navigate to the project directory

```bash
cd SistemaDeControleFinanceiro
```

### 3. Configure the database

Create a PostgreSQL database and configure the connection credentials in `application.properties`.

### 4. Run the application

```bash
mvn spring-boot:run
```

## 🎯 Project Goals

This project is part of my journey as a Backend Developer, helping me develop practical skills in:

- Java programming.
- Spring Boot and REST API development.
- Object-Oriented Programming (OOP).
- Relational database modeling.
- JPA entity relationships.
- DTO and Mapper implementation.
- Dependency Injection.
- Data validation and exception handling.
- Layered architecture and clean code principles.

## 👨‍💻 Author

**Sami Santos**

Backend Developer in progress, continuously learning and building projects with Java and Spring Boot.

- GitHub: [@Samisantos-dev](https://github.com/Samisantos-dev)

---

⭐ Feel free to explore the project and follow my development journey!
