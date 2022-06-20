FROM amazoncorretto:11-alpine-jdk

COPY target/banking-system-app-0.0.1-SNAPSHOT.war app.war

ENTRYPOINT ["java","-jar","/app.war"]