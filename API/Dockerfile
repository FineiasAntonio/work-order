FROM amazoncorretto:17-alpine-jdk

RUN apk add --no-cache maven

COPY pom.xml .

RUN mvn dependency:go-offline

COPY . .