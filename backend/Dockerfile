FROM maven:3.9.7-sapmachine-22 AS build
COPY src /home/app/src
COPY pom.xml /home/app
COPY src/main/resources/docker.application.properties /home/app/src/main/resources/application.properties
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:24-slim-bullseye
COPY --from=build /home/app/target/blogster_rest_api-0.0.1-SNAPSHOT.jar /usr/local/lib/blogster-api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/lib/blogster-api.jar"]
