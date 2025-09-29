# Estágio 1: Construir a aplicação com Maven
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean install -DskipTests

# Estágio 2: Criar a imagem final e leve
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/BoaVaga-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]