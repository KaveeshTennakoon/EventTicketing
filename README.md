# Event Ticketing System

This repository contains the source code for a **Real-Time Event Ticketing System** built with **Spring Boot** for the backend and **Angular** for the frontend. The system is designed to provide users with an efficient and user-friendly platform to browse, book, and manage event tickets in real time.

## Features

- **Event Management**: Create, update, and delete events.
- **User Registration and Authentication**: Secure login and registration for users.
- **Ticket Booking**: Real-time booking and management of tickets.
- **Admin Panel**: Manage events and view ticket sales.
- **Responsive UI**: User-friendly design optimized for multiple devices.

## Technologies Used

### Backend
- **Spring Boot**: Provides a robust and scalable backend.
- **RESTful APIs**: For seamless communication between frontend and backend.
- **MySQL**: Database for managing users, events, and tickets.
- **Spring Security**: For secure user authentication and authorization.

### Frontend
- **Angular**: Responsive and dynamic single-page application.
- **Bootstrap**: For styling and responsive design.
- **RxJS**: For managing asynchronous data streams.

## Getting Started

### Prerequisites
- **Java** (v11 or higher)
- **Node.js** (v14 or higher)
- **Angular CLI** (v12 or higher)
- **MySQL**

### Setup

#### Backend
1. Navigate to the backend folder:
   ```bash
   cd backend
   ```
2. Install dependencies and build the project:
   ```bash
   mvn clean install
   ```
3. Start the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```

#### Frontend
1. Navigate to the frontend folder:
   ```bash
   cd GUI
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the Angular development server:
   ```bash
   ng serve
   ```
4. Access the application at `http://localhost:4200/`.

### Database Configuration
1. Update the `application.properties` file in the backend folder with your MySQL credentials:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/event_ticketing
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```
2. Run the following SQL script to set up the database schema:
   ```sql
   CREATE DATABASE event_ticketing;
   ```

Enjoy using the Real-Time Event Ticketing System! 🎟️
