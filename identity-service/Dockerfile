# Stage 1: build app
# Start with a Maven image that includes JDK 21
FROM maven:3.9.8-amazoncorretto-21 AS build

# Copy source code and file pom.xml to folder /app
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build source code with Maven
RUN mvn package -DskipTests


# Stage 2: create image
# Start with Amazon Corretto JDK 21
FROM amazoncorretto:21.0.4

# Set working folder to App and copy complied file from above step
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Command to run the app
ENTRYPOINT ["java", "-jar", "app.jar"]