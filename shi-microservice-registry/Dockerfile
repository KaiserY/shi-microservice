FROM openjdk:8-jdk-alpine
MAINTAINER KaiserY

ADD ./target/shi-microservice-registry.jar /app/
CMD ["java", "-Xmx128m", "-jar", "-Dspring.profiles.active=docker", "/app/shi-microservice-registry.jar"]

EXPOSE 8701
