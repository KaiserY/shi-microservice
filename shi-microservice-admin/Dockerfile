FROM openjdk:8-jdk-alpine
MAINTAINER KaiserY

ADD ./target/shi-microservice-admin.jar /app/
CMD ["java", "-Xmx128m", "-jar", "-Dspring.profiles.active=docker", "/app/shi-microservice-admin.jar"]

EXPOSE 8703
