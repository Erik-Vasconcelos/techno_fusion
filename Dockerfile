FROM ubuntu:latest AS builder

RUN apt-get update && \
    apt-get install -y openjdk-17-jdk maven && \
    apt-get clean;

WORKDIR /usr/src/app

COPY . /usr/src/app

ARG DATABASE_URL
ARG DATABASE_URL_MIGRATION
ARG DATABASE_USERNAME
ARG DATABASE_PASSWORD

ENV LANG C.UTF-8
ENV LC_ALL C.UTF-8

RUN mvn clean install -DskipTests=true -Dfile.encoding=UTF-8
RUN mvn flyway:migrate

FROM tomcat:9

COPY --from=builder /usr/src/app/target/projeto-techno-fusion-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/app.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
