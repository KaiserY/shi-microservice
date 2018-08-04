FROM openjdk:8-jdk-alpine
MAINTAINER KaiserY

ADD ./target/shi-microservice-zipkin.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/shi-microservice-zipkin.jar"]

EXPOSE 8706
