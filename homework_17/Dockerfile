FROM openjdk:17-jdk-slim
COPY /target/homework_17-*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]