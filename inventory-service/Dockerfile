# Build stage
FROM maven:3.9-eclipse-temurin-17-alpine AS build
WORKDIR /build

# Copy parent pom
COPY pom.xml .

# Copy inventory-service module
COPY inventory-service/pom.xml ./inventory-service/
COPY inventory-service/src ./inventory-service/src

# Build only inventory-service module
RUN mvn -f inventory-service/pom.xml clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build /build/inventory-service/target/*.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]
