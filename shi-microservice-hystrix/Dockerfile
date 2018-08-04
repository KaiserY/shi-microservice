FROM openjdk:8-jdk-alpine
MAINTAINER KaiserY

ADD ./target/shi-microservice-hystrix.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/shi-microservice-hystrix.jar"]

EXPOSE 8706
