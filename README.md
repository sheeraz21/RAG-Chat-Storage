# Chat Service Application

A **Spring Boot REST API** for chat management with PostgreSQL as the database, secured via API key, containerized with Docker, and fully documented with Swagger and Actuator endpoints.

---

## Table of Contents

- [Architecture](#architecture)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [API Security](#api-security)
- [Docker Setup](#docker-setup)
- [Running the Application](#running-the-application)
- [Swagger & Actuator](#swagger--actuator)
- [Environment Variables](#environment-variables)

---

## Architecture

<img width="6215" height="3026" alt="arch" src="https://github.com/user-attachments/assets/5ab0ac87-643a-4b0c-9d00-ebd4f8d4bedc" />

## Layer Explanation:

- Controller Layer: Entry point for all REST endpoints (ChatMessageController, etc.).

- Service Layer: Handles business logic (ChatMessageService).

- DAO Layer: Interacts with the database using EntityManager (ChatMessageDaoImpl).

- PostgreSQL Database: Stores chat sessions and messages.

- ApiKeyFilter: Secures /api/v1/* endpoints.

- Swagger / OpenAPI: API documentation and testing UI.

- Actuator / Metrics: Health checks and application metrics.

## Technology Stack

- Java 17

- Spring Boot 3.3.2

- Spring Data JPA / Hibernate

- PostgreSQL

- Docker & Docker Compose

- Swagger / OpenAPI

- Spring Boot Actuator

## Project Structure
<img width="1093" height="900" alt="p1" src="https://github.com/user-attachments/assets/5e26ee0d-b1bd-4868-b5d6-7d03f040590b" />
<img width="1084" height="915" alt="p2" src="https://github.com/user-attachments/assets/a83ffc2a-e733-45a1-9586-442c8839c819" />

## API Security

All API endpoints under /api/v1/* are protected by API key.

- Header required:
- X-API-KEY: <your-api-key>
- The key is injected via application.properties:
- security.api.key=bXktc2VjcmV0LWFwaS1rZXk=

- Swagger UI and Actuator endpoints are whitelisted and do not require an API key.

## Database
-	PostgreSQL is used as the database.
-	Containerized via Docker Compose.
- Database credentials are managed via .env file:
- POSTGRES_USER=postgres
- POSTGRES_PASSWORD=postgres
- POSTGRES_DB=chatdb

## Environment prop

<img width="1174" height="711" alt="en" src="https://github.com/user-attachments/assets/230232a2-30b4-4c25-9cbf-3454910f4a5f" />

## Docker Setup

- Docker Compose runs both Spring Boot API and PostgreSQL.

<img width="1335" height="894" alt="d1" src="https://github.com/user-attachments/assets/5d97420b-b3b6-47ad-a543-3d1ed472d3de" />

## Dockerfile for Spring Boot:
<img width="830" height="627" alt="d2" src="https://github.com/user-attachments/assets/01013eca-cb6b-4dd7-873e-a82c296b25bd" />

## Running the Application
- Build & Run via Docker Compose

 # Maven Build
- mvn clean install
 
# Build and start containers
- docker compose up --build

# Stop and remove containers
- docker compose down -v

# Services will be available at:
- http://localhost:8080
- PostgreSQL DB accessible at:
- localhost:5432


# Swagger & Actuator

- Swagger UI: http://localhost:8080/swagger-ui/index.html

- OpenAPI JSON: http://localhost:8080/v3/api-docs

- Health Check: http://localhost:8080/actuator/health

- Metrics: http://localhost:8080/actuator/metrics

# Environment Variables
  
- SPRING_DATASOURCE_URL	JDBC URL for PostgreSQL

- SPRING_DATASOURCE_USERNAME	DB username

- SPRING_DATASOURCE_PASSWORD	DB password

- security.api.key	API key for secure endpoints


# Notes

- API key must be provided for /api/v1/* endpoints.

- Swagger and Actuator are accessible without API key.

- PostgreSQL data persists via Docker volume pgdata.

