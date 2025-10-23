FROM openjdk:17-jdk-slim

WORKDIR /app

COPY pom.xml .

COPY src ./src

RUN apt-get update && apt-get install -y maven && \
    mvn -B -f pom.xml clean package

EXPOSE 8080

CMD ["java", "-cp", "target/ShoppingCartProject-1.0-SNAPSHOT.jar", "ShoppingCart"]
