# Banned Books

## Overview
The Banned Books Application is a Java Spring Boot MVP project designed to manage a collection of banned books. It provides functionalities to register users, manage books, and assign genres to books. 
Additionally, it includes authentication and authorization mechanisms for user and admin access.

## Business Case

### Executive Summary
My project consists in developing a user-friendly web application and future mobile application to raise awareness about banned books by providing easy access to curated banned literature. By empowering users with comprehensive information and fostering meaningful discussions, the project strives to promote critical thinking and ignite conversations about freedom of expression and censorship.

### Next Iterations
In following iterations, users will enjoy curated playlist recommendations based on book themes and personalized suggestions for banned books tailored to their tastes.


## Technologies Used
- Java
- Spring Boot
- Spring Security
- Hibernate
- MySQL
- HTML/CSS & Bootstrap (for frontend)
- Thymeleaf (templating engine)
- Jackson (JSON parser)
- Lombok (for reducing boilerplate code)
- Maven (for dependency management)

## Project Structure
The project follows a standard Spring Boot structure with separate packages for models, repositories, services, controllers, and configuration classes.

### Packages
- `org.martavila.bannedbooks.models`: Contains entity classes representing database tables.
- `org.martavila.bannedbooks.repositories`: Contains repository interfaces for database operations.
- `org.martavila.bannedbooks.services`: Contains service interfaces and implementations.
- `org.martavila.bannedbooks.controllers`: Contains controller classes for handling HTTP requests.
- `org.martavila.bannedbooks.exceptions`: Contains custom exception classes for handling specific error scenarios.
- `org.martavila.bannedbooks.config`: Contains Spring security settings.
- `org.martavila.bannedbooks.security`: Contains classes related to security configurations and authentication.

## Setup
1. Ensure you have Java and Maven installed.
2. Clone this repository.
3. Configure your database settings in `application.properties`.
4. Run the application using your IDE or through your terminal by typing `mvn spring-boot:run`.

## Usage
### User Registration
- Access `/user-registration` to register a new user.

### Authentication
- Access `/login` to log in as a user or admin.

### Admin Dashboard
- After logging in, admins are redirected to `/admin-dashboard`.

### Manage Books (Admin)
- Access `/book-registration` to add a new book.
- Access `/books-admin-list` to view and manage all books.
- Access `/book-update` to update book information.
- Use `/book-delete/{isbn}` to delete a book.

### Manage Users (Admin)
- Access `/registered-users` to view all registered users.

### User Dashboard
- After logging in, users are redirected to `/user-dashboard`.

### View Books (User)
- Access `/books-user-list` to view all books.

## Tests

The project includes multiple test classes of the repositories as well as the service implementation classes to ensure the correct functionality of the application.

### Number of Tests: 14 Unit Tests, 1 Parameterized Test and 1 Negative Test

- **Unit Tests**: Test individual methods.
- **Parameterized Tests**: Test with different input parameters.
- **Negative Tests**: Validate the system's behavior under exceptional conditions.

## Acknowledgments
Special thanks to my colleagues at Per Scholas and the staff!
