FROM openjdk:8-jdk-alpine
MAINTAINER KaiserY

ADD ./target/shi-microservice-gateway.jar /app/
CMD ["java", "-Xmx128m", "-jar", "-Dspring.profiles.active=docker", "/app/shi-microservice-gateway.jar"]

EXPOSE 8702
