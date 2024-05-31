FROM amazoncorretto:17-alpine-jdk

RUN apk add --no-cache maven

WORKDIR /app

COPY pom.xml /app

RUN mvn dependency:go-offline

COPY . ./app