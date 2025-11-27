# Build stage
FROM maven:3.9-eclipse-temurin-17-alpine AS build
WORKDIR /build

# Copy parent pom
COPY pom.xml .

# Copy drug-service module
COPY drug-service/pom.xml ./drug-service/
COPY drug-service/src ./drug-service/src

# Build only drug-service module
RUN mvn -f drug-service/pom.xml clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build /build/drug-service/target/*.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]
