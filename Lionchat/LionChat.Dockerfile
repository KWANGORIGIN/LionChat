FROM openjdk:17

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY . .
# COPY src ./src.
# COPY react-frontend ./react-frontend.

CMD ["./mvnw", "spring-boot:run"]