FROM amazoncorretto:11-alpine3.16-jdk
COPY ["target/*.jar", "stats-server-src.jar"]
ENTRYPOINT ["java","-jar","/stats-server-src.jar"]
