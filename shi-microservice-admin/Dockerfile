FROM openjdk:8-jdk-alpine
MAINTAINER KaiserY

ADD ./target/shi-microservice-admin.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/shi-microservice-admin.jar"]

EXPOSE 8705
