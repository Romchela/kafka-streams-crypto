# Kafka-Streams crypto

This test application which calculates current price of different crypto currencies. Information goes to kafka and then price is available by REST API. Coefficients of each stock is located in ```application.yml``` file.

## Dependecies
1) Python3 and kafka-python library
2) Java 8
3) Maven
4) Docker

## How to run
1) Run kafka in docker-container ```docker-container up```
2) Run application ```mvn spring-boot:run```

## How to use
1) Send messages to kafka (you can use tests/producer.py script with 3 arguments: product name, source name, price)
2) Get price of product by REST API GET request ```localhost:8080/api/v1/{product}/price```

## How to run tests
1) Package application: ```mvn clean package```
2) Go to ```tests/``` folder
3) There are several bash scripts which are tests (suffix *test.sh)
3) Run test ```bash run-test.sh large_test```

## Current problems
Tests are not stable for every environment because ```sleep``` is used after commands to run kafka or spring-boot application. So, sometimes (first launching) it takes much time and tests fail. In near future I'm going to integrate application with kafka to docker container, then integrate it to JUnit by Maven plugin and use this approach as test infrastructure.
