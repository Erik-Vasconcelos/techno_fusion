FROM tomcat:9

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

ARG DATABASE_URL
ARG DATABASE_USERNAME
ARG DATABASE_PASSWORD

RUN apt-get install maven -y
RUN mvn clean install -DskipTests=true
RUN mvn flyway:migrate

COPY target/projeto-techno-fusion-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/

EXPOSE 8080

CMD ["catalina.sh", "run"]