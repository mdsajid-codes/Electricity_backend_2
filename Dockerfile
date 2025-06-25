# Step 1: Use OpenJDK 17 image as base
FROM openjdk:17-jdk-slim

# Step 2: Create app directory inside the container
WORKDIR /app

# Step 3: Copy your JAR file into container
COPY target/backend-0.0.1-SNAPSHOT.jar app.jar

# Step 4: Expose the port your app runs on
EXPOSE 8080

# Step 5: Command to run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
