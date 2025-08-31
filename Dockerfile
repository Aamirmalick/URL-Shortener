# ---------- Build Stage ----------
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# ---------- Runtime Stage ----------
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy JAR from builder stage
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]