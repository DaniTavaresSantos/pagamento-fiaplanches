FROM maven:3.9.6-eclipse-temurin-17

ENV SPRING_DATASOURCE_URL=jdbc:postgresql://postgres-db-payment:5432/fiap-lanches-payment
ENV SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka1:19091
WORKDIR /app
RUN rm -rf /app/*
COPY . /app
RUN mvn clean install -DskipTests
RUN mkdir jar
RUN mv /app/target/fiap-lanches-payment-0.0.1-SNAPSHOT.jar /app/jar/fiap-lanches-payment-app.jar
EXPOSE 8084

ENTRYPOINT ["java", "-jar", "/app/jar/fiap-lanches-payment-app.jar"]
