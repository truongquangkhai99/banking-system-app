FROM amazoncorretto:11-alpine-jdk

COPY target/*.war app.war

ENTRYPOINT ["java","-jar","/app.war"]