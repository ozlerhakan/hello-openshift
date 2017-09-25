FROM openjdk:8-alpine

COPY target/hello.jar hello/hello.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "/hello/hello.jar"]
