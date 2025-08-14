# Use official OpenJDK 21 image
FROM eclipse-temurin:21-jdk-jammy

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml first (for caching)
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Copy the source code
COPY src src

# Make Maven wrapper executable
RUN chmod +x mvnw

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose the default Spring Boot port
EXPOSE 8080

# Run the JAR
CMD ["java", "-jar", "target/ticket-management-service-1.0.0.jar"]