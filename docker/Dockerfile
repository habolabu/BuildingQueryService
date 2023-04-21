FROM gradle:jdk17-alpine
WORKDIR /building-query-service

COPY src /building-query-service/src/
COPY build.gradle .
COPY lombok.config .

RUN gradle clean build -x test

ENTRYPOINT ["java", "-jar", "./build/libs/building-query-service-1.0.0.jar"]