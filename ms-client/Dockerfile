FROM ubuntu:latest
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk mysql-client
WORKDIR /app
COPY ms-client/target/ms-client-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "ms-client-0.0.1-SNAPSHOT.jar"]
EXPOSE 8282
