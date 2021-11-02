FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8084
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]