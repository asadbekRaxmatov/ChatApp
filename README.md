# Chat Application

This is a Chat Application that uses Spring Boot, Redis, and PostgreSQL. This README file will guide you on how to set up and run the application using Docker and Docker Compose.

## Prerequisites

Make sure you have the following installed on your machine:

- Docker: [Install Docker](https://docs.docker.com/get-docker/)
- Docker Compose: [Install Docker Compose](https://docs.docker.com/compose/install/)

## Project Structure

The project contains the following main files:

- `Dockerfile`: Defines how the application is packaged into a Docker container.
- `docker-compose.yml`: Defines and runs multi-container Docker applications.
- `src/`: Source code of the Spring Boot application.
- `target/`: Directory where the JAR file of the application is built.

## Dockerfile

The Dockerfile is used to create a Docker image for the Spring Boot application.

```dockerfile
# Base image
FROM openjdk:11-jre-slim

# Maintainer information
LABEL authors="asadb"

# Copy the JAR file from the host into the Docker image
COPY target/your-app-name.jar /app/your-app-name.jar

# Expose the port your application runs on
EXPOSE 8080

# Set the entry point to run the JAR file
ENTRYPOINT ["java", "-jar", "/app/your-app-name.jar"]
