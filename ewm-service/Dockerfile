FROM amazoncorretto:11-alpine3.16-jdk
COPY ["target/*.jar", "ewm-service.jar"]
ENTRYPOINT ["java","-jar","/ewm-service.jar"]
