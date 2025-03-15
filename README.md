# Intro to GraphQL with Java

This project is a **Spring Boot** application designed to introduce and demonstrate how to use **GraphQL** with Java. It provides a simple customer management API, allowing users to query customer data and perform basic CRUD operations using GraphQL.

## 📌 Features
- **GraphQL API** for managing customer data
- **Spring Boot** with **Spring GraphQL**
- **JPA and Hibernate** for database interactions
- **Mutation and Query support** for adding and retrieving customers
- **Subscription support** for real-time updates when customers, orders, or products are added
- **Input validation** to enforce correct data entry and prevent errors
- **Optimized execution with DataLoader to prevent N+1 problem**
- **Asynchronous query execution for better performance**

## 📂 Project Structure
```
/src/main/java/com/graphql/intro
│── controller/
│   ├── CustomerController.java     # Handles GraphQL queries and mutations
│   ├── OrderController.java        # Handles GraphQL queries and mutations for orders
│   ├── ProductController.java      # Handles GraphQL queries and mutations for products
│   ├── SubscriptionController.java # Handles GraphQL subscriptions
│── data/
│   ├── Customer.java               # Entity class representing a customer
│   ├── Order.java                  # Entity class representing an order
│   ├── Product.java                # Entity class representing a product
│   ├── CustomerInput.java          # DTO for customer input handling
│── repo/
│   ├── CustomerRepository.java     # JPA Repository for customer database interactions
│   ├── OrderRepository.java        # JPA Repository for order database interactions
│   ├── ProductRepository.java      # JPA Repository for product database interactions
│── service/
│   ├── SubscriptionService.java    # Manages real-time subscription events
│── exception/
│   ├── GraphQLExceptionHandler.java # Handles validation and database errors in GraphQL
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

### 📡 Subscriptions (Real-time Updates)
GraphQL subscriptions allow clients to listen for real-time updates when new customers, orders, or products are added.

#### Subscribe to new customer additions:
```graphql
subscription {
  customerAdded {
    id
    firstName
    lastName
    email
  }
}
```

#### Subscribe to new orders:
```graphql
subscription {
  orderAdded {
    id
    customer {
      firstName
    }
    salesperson {
      firstName
    }
  }
}
```

#### Subscribe to new products:
```graphql
subscription {
  productAdded {
    id
    name
    price
  }
}
```

#### Steps to Test:
1️⃣ Start the **subscription** query above and keep it running.  
2️⃣ Run the **addOrder** or **addProduct** mutation.  
3️⃣ You will automatically receive the new order/product in your subscription response in real-time.

## ✅ Input Validation
This project includes **GraphQL input validation** to ensure data integrity.

#### Example: Enforcing validation on `addCustomer`
```java
@MutationMapping
public Customer addCustomer(@Valid @Argument(name = "input") CustomerInput customerInput) {
    Customer existingCustomer = customerRepository.findCustomerByEmail(customerInput.getEmail());
    if (existingCustomer != null) {
        throw new IllegalArgumentException("Customer with this email already exists!");
    }
    return customerRepository.save(customerInput.getCustomerEntity());
}
```

### 🔥 Testing Validation
#### ❌ Bad Request (Invalid Email)
```graphql
mutation {
  addCustomer(input: {
    firstName: "John",
    lastName: "Doe",
    email: "invalid-email",
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
✅ **Response:**
```json
{
  "errors": [
    {
      "message": "Validation Error: Email format is invalid."
    }
  ]
}
```

#### ✅ Valid Request (Success)
```graphql
mutation {
  addCustomer(input: {
    firstName: "Jane",
    lastName: "Doe",
    email: "jane.doe@example.com",
    phoneNumber: "+1234567890",
    address: "456 Elm St",
    city: "Los Angeles",
    state: "CA",
    zipCode: "90001"
  }) {
    id
    firstName
    lastName
  }
}
```
## 🛠 GraphQL Execution Optimization

### 🔹 Using DataLoader to Prevent N+1 Problem
GraphQL execution is optimized using **DataLoader**, which batches multiple queries into a single database call.

#### Example: Batching Customer Queries
```java
@QueryMapping
public CompletableFuture<Customer> customerById(@Argument Long id, DataLoader<Long, Customer> customerDataLoader) {
    return customerDataLoader.load(id);
}
```
✅ **Prevents multiple redundant queries and improves database efficiency.**

### 🔹 Enabling Asynchronous Execution
To improve performance, enable async execution in `application.properties`:
```properties
spring.graphql.execution-strategy.query=async
spring.graphql.execution-strategy.mutation=async_serial
```
✅ Queries execute in parallel while mutations remain sequential.

### 🔹 Applying Execution Optimization to Orders and Products
#### Example: Optimizing `orderById` with DataLoader
```java
@QueryMapping
public CompletableFuture<Order> orderById(@Argument Long id, DataLoader<Long, Order> orderDataLoader) {
    return orderDataLoader.load(id);
}
```
#### Example: Optimizing `productById` with DataLoader
```java
@QueryMapping
public CompletableFuture<Product> productById(@Argument Long id, DataLoader<Long, Product> productDataLoader) {
    return productDataLoader.load(id);
}
```
✅ **This ensures efficient batch loading of orders and products, reducing database queries.**

## 📚 Learning Resources
- [GraphQL Java Documentation](https://www.graphql-java.com/documentation/)
- [Spring GraphQL](https://docs.spring.io/spring-graphql/docs/current/reference/html/)
- [GraphQL vs REST](https://graphql.org/learn/comparison/)

