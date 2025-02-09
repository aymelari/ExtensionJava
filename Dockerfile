# Stage 1: Build the JAR using Gradle (Java 17)
FROM gradle:8.7.0-jdk17 AS build
WORKDIR /app
COPY build.gradle .
COPY settings.gradle .
COPY src ./src
COPY gradlew .
COPY gradlew.bat .
COPY gradle ./gradle
RUN chmod +x gradlew
RUN ./gradlew clean build -x test

# Stage 2: Runtime image (Java 17)
FROM eclipse-temurin:17-jre-jammy
COPY --from=build /app/build/libs/*.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]