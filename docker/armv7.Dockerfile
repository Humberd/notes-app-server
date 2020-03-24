FROM gradle:6.2.2-jdk11 AS builder
WORKDIR /usr/project
COPY . .
RUN gradle build -x test

FROM adoptopenjdk/openjdk11:armv7l-ubuntu-jdk-11.0.6_10-slim
WORKDIR /usr/run
COPY --from=builder /usr/project/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
