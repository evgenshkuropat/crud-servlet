# CRUD Servlet API (Orders) — Educational Project

Java-based REST-style API for managing orders using Servlets and JSON.

This project demonstrates core backend concepts such as request handling, CRUD operations, JSON serialization, and basic REST principles.

---

## 🚀 Tech Stack
- Java
- Jakarta Servlet API
- Apache Tomcat
- Jackson (ObjectMapper)
- Maven

---

## 📌 Features
- Create, read, update and delete orders (CRUD)
- REST-style endpoints
- JSON request and response handling
- In-memory thread-safe storage using `ConcurrentHashMap`
- Support for `LocalDate` via `JavaTimeModule`
- Clean and simple backend structure

---

## 🔗 API Endpoints

| Method | Endpoint        | Description                  |
|--------|------------------|------------------------------|
| POST   | `/orders`        | Create a new order           |
| GET    | `/orders`        | Get all orders               |
| GET    | `/orders/{id}`   | Get order by ID              |
| PUT    | `/orders`        | Update existing order        |
| DELETE | `/orders/{id}`   | Delete order by ID           |

---

## 🧪 Notes
- This project uses in-memory storage for simplicity and learning purposes.
- Data will be reset after application restart.
- Designed as an educational example of building REST APIs with Servlets.

---

## ▶️ How to Run
1. Build the project:
   ```bash
   mvn clean package
