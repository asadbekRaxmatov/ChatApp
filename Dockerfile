#FROM ubuntu:latest
#LABEL authors="asadb"
#
#ENTRYPOINT ["top", "-b"]


# Base image
FROM openjdk:11-jre-slim

# Maintainer information
LABEL authors="asadb"

# Copy the JAR file from the host into the Docker image
COPY target/chat-app.jar /app/chat-app.jar

# Expose the port your application runs on
EXPOSE 8080

# Set the entry point to run the JAR file
ENTRYPOINT ["java", "-jar", "/app/chat-app.jar"]
