# Build stage
FROM maven:3.9-eclipse-temurin-17-alpine AS build
WORKDIR /build

# Copy parent pom
COPY pom.xml .

# Copy auth-service module
COPY auth-service/pom.xml ./auth-service/
COPY auth-service/src ./auth-service/src

# Build only auth-service module
RUN mvn -f auth-service/pom.xml clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build /build/auth-service/target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
