#
# Build stage
#
FROM maven:3.8.4-openjdk-17-slim AS build
COPY src /app/src
COPY react-frontend /app/react-frontend
COPY pom.xml /app
RUN mvn -f /app/pom.xml clean package -DskipTests

#
# Package stage
#
FROM openjdk:17
ARG --from=build JAR_FILE=/app/target/*.jar
COPY --from=build ${JAR_FILE} app.jar
CMD java -jar app.jar
EXPOSE 8080