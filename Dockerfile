FROM tomcat:9 AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

ARG DATABASE_URL
ARG DATABASE_USERNAME
ARG DATABASE_PASSWORD

RUN apt-get install maven -y
RUN mvn clean install -DskipTests=true
RUN mvn flyway:migrate

EXPOSE 8080

COPY --from=build /target/projeto-techno-fusion-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/

CMD ["catalina.sh", "run"]