FROM openjdk:8-jdk-alpine
MAINTAINER KaiserY

ADD ./target/shi-microservice-zipkin.jar /app/
CMD ["java", "-Xmx200m", "-jar", "-Dspring.profiles.active=docker", "/app/shi-microservice-zipkin.jar"]

EXPOSE 8705
