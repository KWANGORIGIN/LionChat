# only works on mac for some reason
# FROM openjdk:17

# WORKDIR /app

# COPY .mvn/ .mvn
# COPY mvnw pom.xml ./
# RUN ./mvnw dependency:go-offline

# COPY . .
# # COPY src ./src.
# # COPY react-frontend ./react-frontend.

# CMD ["./mvnw", "spring-boot:run"]

# must run "mvn clean package -DskipTests" to convert this to a jar file first
FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
# ENTRYPOINT ["java","-jar","/app.jar"]
CMD java -jar app.jar
EXPOSE 8080