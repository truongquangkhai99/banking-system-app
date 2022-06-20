# Banking System App<hr>

## Before installation
To start the project, you need to install docker and docker-compose. You should also check if you have at least JRE 11 and, if not, install it.

Docker Install: https://docs.docker.com/engine/install/

JRE install: https://docs.oracle.com/goldengate/1212/gg-winux/GDRAD/java.htm#BGBFHBEA

## How to install
Dev:

Change string to 'dev' on 3 row in [application.yml](src/main/resources/application.yml)
```
# docker-compose up

# ./mwvn clean install

# java -jar target/*.war
```
Prod:

Change string to 'prod' on 3 row in [application.yml](src/main/resources/application.yml)
```
# docker-compose up
```

## Features
- [x] Add a profiles ('dev' and 'prod')
- [x] Add a docker-compose for db and spring boot app
- [ ] Add a Spring Security with JWT tokens
- [ ] Add a Swagger Doc and UI
- [ ] Add a AOP
- [ ] Add a UI (Thymeleaf with HTML and CSS or perhaps Angular)
- [ ] Add a Makefile

## Entity Relationship Diagram
![plot](src/main/resources/erd/erd.svg)