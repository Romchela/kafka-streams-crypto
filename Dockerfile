FROM maven:3.5-jdk-8 as build

WORKDIR /app

COPY . .

RUN mvn package -DskipTests

FROM java:8-jdk

WORKDIR /app

COPY --from=build /app/target/crypto-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-jar", "/app/crypto-0.0.1-SNAPSHOT.jar"]