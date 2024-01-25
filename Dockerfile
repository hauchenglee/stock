# Start with base image
FROM openjdk:17
# Add Maintainer Info
LABEL maintainer="hauchenglee"
# Add a temporary volume
VOLUME /tmp
# Expose Port 8085
EXPOSE 8085
# Application Jar File
ARG JAR_FILE=target/springboot-stock.jar
# Add Application Jar File to the Container
ADD ${JAR_FILE} springboot-stock.jar
# Run the JAR file
ENTRYPOINT ["java", "-jar", "/springboot-stock.jar"]