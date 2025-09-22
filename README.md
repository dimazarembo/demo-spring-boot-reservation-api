# Spring Boot Reservation Demo

This project demonstrates the capabilities of Spring Boot by implementing a reservation system with various features. It includes integration with a PostgreSQL database and a Swagger UI for API documentation.

## Technologies
- ![Java 17](https://img.shields.io/badge/Java-17-blue)
- ![Spring 3.5.5](https://img.shields.io/badge/Spring-3.5.5-green)
- ![Springdoc 2.8.13](https://img.shields.io/badge/Springdoc-2.8.13-orange)
- ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-9.6+-lightgrey)

## Features
- Create a new reservation (with a default PENDING status)
- Cancel a reservation
- Retrieve a list of reservations with pagination
- Get a reservation by ID
- Approve a reservation
- Check availability by room and date range

### Reservation Statuses
- PENDING
- APPROVED
- CANCELLED

### Availability Check
The system checks room availability based on start and end dates.

## API Endpoints
- `POST /reservation/availability/check` - Check room availability
- `DELETE /reservation/{id}/cancel` - Cancel a reservation
- `GET /reservation/{id}` - Get reservation by ID
- `GET /reservation` - Get list of reservations with pagination
- `POST /reservation` - Create a new reservation
- `POST /reservation/{id}/approve` - Approve a reservation
- `PUT /reservation/{id}` - Update a reservation

## Database Schema
### Reservation
- `id` (int64)
- `userId` (int64)
- `roomId` (int64)
- `startDate` (string, date)
- `endDate` (string, date)
- `status` (string)

### CheckAvailabilityRequest
- `roomId` (int64)
- `startDate` (string, date)
- `endDate` (string, date)

### CheckAvailabilityResponse
- `message` (string)
- `status` (string)

## Setup

1. Install Java 17 and Maven.
2. Clone the repository: `git clone https://github.com/dimazarembo/demo-spring-boot-reservation-api.git`
3. Navigate to the project directory: `cd demo-spring-boot-reservation-api`
4. Rename `application-example.properties` to `application.properties`

```bash
cd demo-spring-boot-reservation-api/src/main/resources
mv application-example.properties application.properties
```

5. Add your credentials for database access

```bash
nano application.properties
```

6. Run the application: `mvn spring-boot:run`
7. The API is available at `http://localhost:8080`

## Database Setup
### Populate Database
Use the following SQL script to populate the reservations table with sample data:

```sql
INSERT INTO reservations (user_id, room_id, start_date, end_date, status)
SELECT
    (random() * 4 + 1)::int AS user_id,          -- users 1–5
    (random() * 10 + 1)::int AS room_id,         -- rooms 1–10
    current_date + gs.id AS start_date,          -- start date = today + N days
    current_date + gs.id + (random() * 10 + 1)::int AS end_date, -- end date +1..10 days
    (ARRAY['APPROVED','CANCELLED','PENDING'])[floor(random()*3 + 1)]::text AS status
FROM generate_series(1, 250) gs(id);
