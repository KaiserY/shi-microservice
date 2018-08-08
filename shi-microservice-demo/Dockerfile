FROM openjdk:8-jdk-alpine
MAINTAINER KaiserY

ADD ./target/shi-microservice-demo.jar /app/
CMD ["java", "-Xmx128m", "-jar", "-Dspring.profiles.active=docker", "/app/shi-microservice-demo.jar"]

EXPOSE 8704
