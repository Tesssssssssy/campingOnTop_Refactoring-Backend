FROM openjdk:11-jdk-slim-stretch
COPY target/campingOnTop-0.0.1-SNAPSHOT.jar /api-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "/api-0.0.1-SNAPSHOT.jar"]