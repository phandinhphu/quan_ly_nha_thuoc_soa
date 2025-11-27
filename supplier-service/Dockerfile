# Build stage
FROM maven:3.9-eclipse-temurin-17-alpine AS build
WORKDIR /build

# Copy parent pom
COPY pom.xml .

# Copy supplier-service module
COPY supplier-service/pom.xml ./supplier-service/
COPY supplier-service/src ./supplier-service/src

# Build only supplier-service module
RUN mvn -f supplier-service/pom.xml clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build /build/supplier-service/target/*.jar app.jar

EXPOSE 8084

ENTRYPOINT ["java", "-jar", "app.jar"]
