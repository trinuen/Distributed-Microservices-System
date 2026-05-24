# Distributed Order Processing System

A lightweight distributed microservices project built with **Spring Boot**, **AWS SQS**, and **SQLite** that demonstrates asynchronous communication between services using message queues.

The system consists of three independent services:

- **Order Service** — receives customer orders and publishes `OrderCreated` events
- **Inventory Service** — subscribes to order events and decrements product stock
- **Notification Service** — subscribes to order events and sends confirmation notifications (logged to console)

This project showcases event-driven architecture and decoupled communication using AWS SQS.

---

# Architecture

```text
                ┌──────────────────┐
                │   Client Request │
                └────────┬─────────┘
                         │
                         ▼
                ┌──────────────────┐
                │  Order Service   │
                │ Spring Boot API  │
                └────────┬─────────┘
                         │
         Publishes OrderCreated Event
                         │
        ┌────────────────┴────────────────┐
        ▼                                 ▼
┌──────────────────┐            ┌────────────────────┐
│ Inventory Queue  │            │ Notification Queue │
│     AWS SQS      │            │      AWS SQS       │
└────────┬─────────┘            └─────────┬──────────┘
         ▼                                ▼
┌──────────────────┐            ┌────────────────────┐
│ Inventory Service│            │ NotificationService│
│ Decrement Stock  │            │ Console Logging    │
└──────────────────┘            └────────────────────┘
```

---

# Features

- Distributed microservice architecture
- Asynchronous communication using AWS SQS
- Event-driven design
- Inventory stock management
- Order confirmation notifications
- SQLite database persistence
- REST API using Spring Boot
- Queue polling with SQS listeners

---

# Tech Stack

- **Java**
- **Spring Boot**
- **Spring Data JPA**
- **AWS SQS**
- **SQLite**
- **Gradle**

---

# Services

## 1. Order Service

Responsible for:

- Receiving incoming orders through REST endpoints
- Persisting orders to SQLite
- Publishing `OrderCreated` events to SQS queues

### Example Endpoint

```http
POST /orders
```

### Example Request Body

```json
{
  "productName": "Laptop",
  "quantity": 2
}
```

---

## 2. Inventory Service

Responsible for:

- Polling messages from the inventory queue
- Consuming `OrderCreated` events
- Decrementing inventory stock in SQLite

### Example Console Output

```text
Received order for Laptop
Updated inventory stock: 13 remaining
```

---

## 3. Notification Service

Responsible for:

- Polling messages from the notification queue
- Sending order confirmations
- Logging notifications to the console

### Example Console Output

```text
Order confirmation sent for Laptop
```

---

# AWS SQS Queues

The project uses two separate SQS queues:

| Queue | Purpose |
|---|---|
| `inventory-queue` | Handles inventory updates |
| `notification-queue` | Handles order confirmations |

When an order is created:

1. Order Service publishes messages to both queues
2. Inventory Service polls the inventory queue
3. Notification Service polls the notification queue

---

# Project Structure

```text
distributed-order-system/
│
├── order-service/
│
├── inventory-service/
│
├── notification-service/
│
└── README.md
```

---

# Database

SQLite is used as the lightweight relational database for persistence.

Example entities:

- Orders
- Products / Inventory

---

# Example Workflow

1. Client sends POST request to Order Service
2. Order Service stores order in database
3. Order Service publishes messages to:
    - Inventory Queue
    - Notification Queue
4. Inventory Service receives event and updates stock
5. Notification Service receives event and logs confirmation

---
