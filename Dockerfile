FROM openjdk:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENV SERVER_PORT=9090
ENTRYPOINT ["java","-jar","/app.jar"]