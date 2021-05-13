FROM openjdk:11-jre-slim
LABEL maintainer="Luiz Moraes"
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080