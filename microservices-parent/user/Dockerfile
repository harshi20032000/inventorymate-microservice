# Use Java 21 runtime
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Copy jar
COPY target/*.jar app.jar

# Run app
ENTRYPOINT ["java", "-jar", "app.jar"]