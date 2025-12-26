## CRUD Servlet API (Orders) — Educational Project

Java Servlet-based REST-style API for managing Orders using JSON.

### Tech Stack
- Java
- Jakarta Servlet API
- Apache Tomcat
- Jackson (ObjectMapper)
- Maven

### Endpoints
- POST `/orders` — create order (JSON)
- GET `/orders` — list all orders
- GET `/orders/{id}` — get order by id
- PUT `/orders` — update order (by id in JSON body)
- DELETE `/orders/{id}` — delete order

### Notes
- Uses in-memory thread-safe storage (`ConcurrentHashMap`) for simplicity.
- Supports `LocalDate` via `JavaTimeModule`.
