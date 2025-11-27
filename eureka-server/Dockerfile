# Build stage
FROM maven:3.9-eclipse-temurin-17-alpine AS build
WORKDIR /build

# Copy parent pom
COPY pom.xml .

# Copy eureka-server module
COPY eureka-server/pom.xml ./eureka-server/
COPY eureka-server/src ./eureka-server/src

# Build only eureka-server module
RUN mvn -f eureka-server/pom.xml clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build /build/eureka-server/target/*.jar app.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "app.jar"]
