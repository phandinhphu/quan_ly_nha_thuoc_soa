# Build stage
FROM maven:3.9-eclipse-temurin-17-alpine AS build
WORKDIR /build

# 1. Copy file pom.xml gốc (Parent Pom) - Rất quan trọng cho dự án đa module
COPY pom.xml .

# 2. Copy module reporter-service 
COPY reporter-service/pom.xml ./reporter-service/
COPY reporter-service/src ./reporter-service/src

# 3. Biên dịch riêng module reporter-service
# Lệnh này sẽ tự động giải quyết các class trong package com.pma.reporting
RUN mvn -f reporter-service/pom.xml clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# 4. Copy file JAR đã build từ công đoạn trước
COPY --from=build /build/reporter-service/target/*.jar app.jar

# 5. Mở port 8086 theo yêu cầu của bạn
EXPOSE 8086

ENTRYPOINT ["java", "-jar", "app.jar"]