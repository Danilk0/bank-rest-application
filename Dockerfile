FROM maven:3.6.4-jdk-17 AS build
COPY . /app
WORKDIR /app
RUN mvn clean package

FROM openjdk:17-jdk-slim
COPY --from=build /app/target/*.jar /app.jar
CMD ["java", "-jar", "/app.jar"]