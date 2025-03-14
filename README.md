# Intro to GraphQL with Java

This project is a **Spring Boot** application designed to introduce and demonstrate how to use **GraphQL** with Java. It provides a simple customer management API, allowing users to query customer data and perform basic CRUD operations using GraphQL.

## 📌 Features
- **GraphQL API** for managing customer data
- **Spring Boot** with **Spring GraphQL**
- **JPA and Hibernate** for database interactions
- **Mutation and Query support** for adding and retrieving customers

## 📂 Project Structure
```
/src/main/java/com/graphql/intro
│── controller/
│   ├── CustomerController.java     # Handles GraphQL queries and mutations
│── data/
│   ├── Customer.java               # Entity class representing a customer
│   ├── CustomerInput.java          # DTO for input handling
│── repo/
│   ├── CustomerRepository.java     # JPA Repository for database interactions
│── Application.java                # Main Spring Boot application
```

## 🚀 Getting Started

### 1️⃣ Prerequisites
- **Java 17+**
- **Maven**
- **Spring Boot**
- **GraphQL Java**
- **H2 / MySQL Database (configured separately if needed)**

### 2️⃣ Installation & Setup
1. Clone the repository:
   ```sh
   git clone https://github.com/your-repo/graphql-java-intro.git
   cd graphql-java-intro
   ```
2. Build and run the application:
   ```sh
   mvn spring-boot:run
   ```
3. Access the GraphQL Playground:
    - **GraphQL Endpoint**: `http://localhost:8080/graphql`

## 🛠 Usage

### 🔍 Queries
#### Fetch all customers:
```graphql
{
  customers {
    id
    firstName
    lastName
    email
  }
}
```
#### Fetch a customer by ID:
```graphql
{
  customerById(id: 1) {
    firstName
    lastName
    email
  }
}
```

#### Fetch a customer by Email:
```graphql
{
  customerByEmail(email: "test@example.com") {
    firstName
    lastName
  }
}
```

### ✏️ Mutations
#### Add a new customer:
```graphql
mutation {
  addCustomer(input: {
    firstName: "John",
    lastName: "Doe",
    email: "john.doe@example.com",
    phoneNumber: "123456789",
    address: "123 Main St",
    city: "New York",
    state: "NY",
    zipCode: "10001"
  }) {
    id
    firstName
    lastName
  }
}
```

## 📚 Learning Resources
- [GraphQL Java Documentation](https://www.graphql-java.com/documentation/)
- [Spring GraphQL](https://docs.spring.io/spring-graphql/docs/current/reference/html/)
- [GraphQL vs REST](https://graphql.org/learn/comparison/)

