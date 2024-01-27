FROM openjdk:17-jdk-slim AS builder

RUN apt-get update && apt-get install -y maven && apt-get clean

COPY . /usr/src/app

WORKDIR /usr/src/app

ARG DATABASE_URL
ARG DATABASE_URL_MIGRATION
ARG DATABASE_USERNAME
ARG DATABASE_PASSWORD

RUN mvn clean install -DskipTests=true

RUN mvn flyway:migrate

FROM tomcat:9-jdk17-openjdk-slim

EXPOSE 8080

COPY --from=builder /usr/src/app/target/projeto-techno-fusion-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/

CMD ["catalina.sh", "run"]
