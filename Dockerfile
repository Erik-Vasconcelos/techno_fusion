FROM ubuntu:latest AS builder

RUN apt-get update && apt-get install -y openjdk-17-jdk maven && apt-get clean;

COPY . /usr/src/app

ARG DATABASE_URL
ARG DATABASE_URL_MIGRATION
ARG DATABASE_USERNAME
ARG DATABASE_PASSWORD

WORKDIR /usr/src/app

RUN mvn clean install -DskipTests=true
RUN mvn flyway:migrate

FROM tomcat:9

COPY --from=builder /usr/src/app/target/projeto-techno-fusion-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/app.war

EXPOSE 8080

CMD ["catalina.sh", "run"]