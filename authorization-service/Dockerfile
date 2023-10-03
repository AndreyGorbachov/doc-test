#FROM maven:3.8-openjdk-17 as builder
#WORKDIR /app
#COPY . /app/.
#RUN mvn -f /app/pom.xml clean package -Dmaven.test.skip=true

FROM openjdk:17-alpine
WORKDIR /app
EXPOSE 9000
CMD ["java", "-jar", "/app/authorization.jar"]
COPY /target/authorization-*.jar authorization.jar
