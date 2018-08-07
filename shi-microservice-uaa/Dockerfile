FROM openjdk:8-jdk-alpine
MAINTAINER KaiserY

ADD ./target/shi-microservice-uaa.jar /app/
CMD ["java", "-Xmx200m", "-jar", "-Dspring.profiles.active=docker", "/app/shi-microservice-uaa.jar"]

EXPOSE 8709
