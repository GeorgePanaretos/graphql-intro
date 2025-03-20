
# 📌 GraphQL Queries, Mutations, and Subscriptions

## 🔍 **Queries**
### 1️⃣ **Fetch All Customers**
```graphql
query {
  customers {
    id
    firstName
    lastName
    email
    address
    zipCode
    city
    phoneNumber
    __typename
  }
}
```
**Description:** Retrieves a list of all customers along with their basic information.

---

### 2️⃣ **Fetch Customer by Email**
```graphql
query {
  customerByEmail(email: "eramos2@plala.or.jp") {
    id
    firstName
    lastName
    email
    address
    zipCode
    city
    phoneNumber
    __typename
  }
}
```
**Description:** Retrieves customer details using their email.

---

### 3️⃣ **Fetch All Products**
```graphql
query {
  products {
    id
    name
    status
    variety
    price
    size
  }
}
```
**Description:** Retrieves a list of all products available in the system.

---

### 4️⃣ **Fetch Order by ID**
```graphql
query {
  orderById(id: "H2-1203") {
    id
    customer {
      firstName
      lastName
    }
    orderLines {
      product {
        name
      }
      quantity
    }
  }
}
```
**Description:** Fetches details of a specific order using its unique ID.

---

### 5️⃣ **Fetch All Orders**
```graphql
query {
  orders {
    id
    customer {
      firstName
      lastName
    }
    salesperson {
      phoneNumber
    }
  }
}
```
**Description:** Retrieves a list of all orders, including customer and salesperson details.

---

### 6️⃣ **Introspection Query - Fetch Schema Types**
```graphql
{
  __schema {
    types {
      name
      kind
      description
    }
  }
}
```
**Description:** Fetches all GraphQL types available in the schema.

---

### 7️⃣ **Introspection Query - Fetch Queries & Mutations**
```graphql
{
  __schema {
    queryType {
      fields {
        name
        description
      }
    }
    mutationType {
      fields {
        name
        description
      }
    }
  }
}
```
**Description:** Lists all available GraphQL queries and mutations.

---

## 🔄 **Mutations**
### 8️⃣ **Add a New Customer**
```graphql
mutation {
  addCustomer(input: {
    firstName: "John"
    lastName: "Doe"
    email: "john.doe@google.com"
    phoneNumber: "+421951321789"
    address: "Up State 12"
    city: "Anytown"
    state: "KS"
    zipCode: "45689"
  }) {
    id
  }
}
```
**Description:** Creates a new customer in the system.

---

### 9️⃣ **Add Another Customer**
```graphql
mutation {
  addCustomer(input: {
    firstName: "Kyrios"
    lastName: "Poutsa"
    email: "koulasa@mail.com"
    phoneNumber: "6935499"
    address: "Palamari 24"
    city: "Malakia"
    state: "KS"
    zipCode: "1234"
  }) {
    id
    firstName
    lastName
    email
  }
}
```
**Description:** Adds another customer with different details.

---

### 🔟 **Add a New Product**
```graphql
mutation {
  addProduct(input: {
    name: "Soda"
    size: 20
    variety: "Green Apple"
    price: 1.99
    status: "ACTIVE"
  }) {
    success
    message
    data {
      id
      name
      size
      variety
      price
      status
    }
  }
}
```
**Description:** Adds a new product to the system.

---

### 1️⃣1️⃣ **Create a New Order**
```graphql
mutation {
  addOrder(input: {
    customerId: 1,
    salespersonId: 2,
    orderLines: [
      { productId: "MWBLU20", quantity: 3 }
    ]
  }) {
    success
    message
    data {
      id
      customer {
        firstName
      }
      orderLines {
        product {
          name
        }
        quantity
      }
    }
  }
}
```
**Description:** Creates a new order for a customer with specific products.

---

### 1️⃣2️⃣ **Update Customer Details**
```graphql
mutation {
  updateCustomer(id: 1, input: {
    firstName: "Updated Name"
    lastName: "Updated Last Name"
    email: "updated.email@example.com"
    phoneNumber: "9876543210"
    address: "Updated Address"
    city: "Updated City"
    state: "UT"
    zipCode: "99999"
  }) {
    success
    message
    data {
      id
      firstName
      lastName
    }
  }
}
```
**Description:** Updates customer details by their unique ID.

---

### 1️⃣3️⃣ **Delete a Customer**
```graphql
mutation {
  deleteCustomer(id: 1) {
    success
    message
  }
}
```
**Description:** Deletes a customer from the system.

---

## 📡 **Subscriptions**
### 1️⃣4️⃣ **Listen for New Customers**
```graphql
subscription {
  customerAdded {
    firstName
    lastName
    email
    phoneNumber
    address
    city
    state
    zipCode
  }
}
```
**Description:** Subscribes to real-time updates when a new customer is added.

---
### 1️⃣5️⃣5️**Update Order Details**

```graphql
mutation {
  updateOrder(id: "H2-1203", input: {
    customerId: "1",
    salespersonId: "2",
    orderLines: [
      { productId: "MWBLU20", quantity: 5 },
      { productId: "MWSTR32", quantity: 2 }
    ]
  }) {
    success
    message
    data {
      id
      customer {
        firstName
      }
      salesperson {
        firstName
      }
      orderLines {
        product {
          name
        }
        quantity
      }
    }
  }
}
```
**Description:** Subscribes to real-time updates when a new order is added.

---
### 1️⃣6️⃣ **Delete an Order**

```graphql
mutation {
  deleteOrder(id: "7cdb5d9c-42fb-42d6-859a-b80edffc6c95") {
    success
    message
    data
  }
}
```

**Description:** Deletes an order from the system.

---