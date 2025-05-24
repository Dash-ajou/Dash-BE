FROM openjdk:23

WORKDIR /app

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} dash-be.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "dash-be.jar"]
