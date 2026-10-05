# Login Activity Monitor for Suspicious Behaviour

A Spring Boot application designed to monitor login activities, detect suspicious login behaviour, apply rate limiting, manage user sessions, and implement role-based access control.

## Features

### Login Activity Monitoring

- Record login attempts with username, IP address, timestamp, and status.
- Track successful and failed login attempts.
- Retrieve login attempts.
- Filter login attempts by username, IP address, and status.

### Suspicious Activity Detection

The application detects suspicious login behaviour based on:

- Multiple failed login attempts from the same IP address within 10 minutes.
- Multiple failed login attempts for the same username within 10 minutes.
- Successful login immediately following multiple failed login attempts.
- Suspicious activities are stored with the reason, username, IP address, and timestamp.

### Input Validation

- Validates username, IP address, and login status.
- Uses Jakarta Bean Validation annotations such as `@NotBlank` and `@NotNull`.

### Rate Limiting

- Limits login requests to 5 attempts per minute for each IP address.
- Returns HTTP 429 Too Many Requests when the limit is exceeded.
- Uses a custom `RateLimitExceededException`.
- Uses global exception handling with `@RestControllerAdvice`.

### Session Management

- Creates user sessions with unique session IDs.
- Tracks session creation time and last activity.
- Sessions expire after 15 minutes of inactivity.
- Provides an API to retrieve active sessions.

### Role-Based Access Control

The application supports the following roles:

- USER
- ADMIN
- SUPERADMIN

Role-based access is implemented using Spring Security.

### Password Security

- Uses BCrypt password hashing.
- Passwords are not stored as plain text for newly created users.
- Uses `PasswordEncoder` for password verification.

## Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- Spring Security
- Hibernate
- MySQL
- Maven
- Jakarta Validation
- Postman

## Project Structure

```text
src/main/java/com/example/LoginActivityMonitorForSuspiciousBehaviourApplication

├── Controller
├── Service
├── Repository
├── Entity
├── Dto
├── Enum
├── Exception
└── Security
