# Use Eclipse Temurin JDK 17 (slim)
FROM eclipse-temurin:17-jdk-jammy

# Set working directory
WORKDIR /app

# Copy jar and .env
COPY target/chat-service-0.0.1-SNAPSHOT.jar app.jar
COPY .env /app/.env

# Expose port
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
