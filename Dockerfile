FROM maven:latest

ENV SPRING_DATASOURCE_URL=jdbc:postgresql://postgres-db:5434/fiap-lanches-payment
WORKDIR /app
RUN rm -rf /app/*
COPY . /app
RUN mvn clean install -DskipTests
RUN mkdir jar
RUN mv /app/target/fiap-lanches-payment-0.0.1-SNAPSHOT.jar /app/jar/fiap-lanches-payment-app.jar
EXPOSE 8083

ENTRYPOINT ["java", "-jar", "/app/jar/fiap-lanches-payment-app.jar"]
