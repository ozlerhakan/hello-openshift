FROM maven:3-alpine

COPY pom.xml hello/

COPY src/ hello/src/

WORKDIR hello/

RUN mvn clean install

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "/hello/target/hello.jar"]
