# 🎬 Ticket Management API Service

A lightweight **Movie Ticket Booking System** built with **Spring Boot**.  
This service allows managing movies, screens, showtimes, and bookings, with additional analytics endpoints.

---

## 🚀 Features

### Core Requirements
- Manage multiple screens, each with seating capacity & price
- Register movies with duration
- Create showtimes for movies in specific screens
- Book seats for a showtime
- Prevent overbooking (with clear error messages)
- Cancel bookings & free up seats

### Bonus Features
- Track user booking history
- Fetch all bookings
- Identify most popular showtimes
- Find peak booking hours

---

## 📦 Tech Stack
- **Backend**: Java + Spring Boot
- **Database**: neon.tech
- **Build Tool**: Maven/Gradle
- **Deployment**: [Render](https://render.com)

---

## ⚙️ Setup & Run Instructions

### Prerequisites
- JDK 17+
- Maven (if building locally)

### Steps
1. Clone the repo:
   ```bash
   https://github.com/sRAVanI417/ticket-management-service.git
   cd ticket-management-service

URL for the APIs are : https://ticket-management-service.onrender.com

DB here : https://console.neon.tech/app/projects/holy-bird-00805983/branches/br-delicate-grass-a8a1e97h/tables


| Controller             | Endpoint                                | Method | Path/Query Params                    | Request Body                                                                                                 | Response                                       |
|------------------------|-----------------------------------------|--------|--------------------------------------|--------------------------------------------------------------------------------------------------------------|------------------------------------------------|
| **MovieController**    | `/movies`                               | POST   | -                                    | `{ "name": "Avengers", "description": "MCU assemble" }`                                                      | Created `Movie` object                         |
|                        | `/movies`                               | GET    | -                                    | -                                                                                                            | List of all `Movie` objects                    |
| **ShowtimeController** | `/showtimes`                            | POST   | -                                    | `{ "movie": { "id": 1 }, "screen": { "id": 1 }, "startTime": "2025-08-15T10:30:00", "availableSeats": 120 }` | Created `Showtime` object                      |
|                        | `/showtimes/{id}/availability`          | GET    | Path: `id` (showtime ID)             | -                                                                                                            | Integer (available seats)                      |
| **ScreenController**   | `/screens`                              | POST   | -                                    | `{ "name": "Audi 1", "seatingCapacity": 120, "price": 200 }`                                                 | Created `Screen` object                        |
|                        | `/screens/{screenId}`                   | DELETE | Path: `screenId`                     | -                                                                                                            | String message confirming deletion             |
| **BookingController**  | `/bookings`                             | POST   | -                                    | `{ "showtimeId": 1, "userName": "John", "seatCount": 2 }`                                                    | String message (booking confirmation or error) |
|                        | `/bookings/{id}`                        | DELETE | Path: `id` (booking ID)              | -                                                                                                            | String message (cancellation confirmation)     |
|                        | `/bookings`                             | GET    | -                                    | -                                                                                                            | List of all `Reservation` objects              |
|                        | `/bookings/users/{username}`            | GET    | Path: `username`                     | -                                                                                                            | List of `Reservation` objects for user         |
|                        | `/bookings/analytics/popular-showtimes` | GET    | Query: `limit` (optional, default=5) | -                                                                                                            | List of top showtimes with booking count       |
|                        | `/bookings/analytics/peak-hours`        | GET    | -                                    | -                                                                                                            | List of hours with booking count (descending)  |
