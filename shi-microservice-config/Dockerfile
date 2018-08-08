FROM openjdk:8-jdk-alpine
MAINTAINER KaiserY

ADD ./target/shi-microservice-config.jar /app/
CMD ["java", "-Xmx128m", "-jar", "-Dspring.profiles.active=docker", "/app/shi-microservice-config.jar"]

EXPOSE 8700
