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

RUN apt-get update && apt-get install -y vim
RUN echo "deb http://us-west-2.ec2.archive.ubuntu.com/ubuntu/ trusty multiverse  deb http://us-west-2.ec2.archive.ubuntu.com/ubuntu/ trusty-updates multiverse  deb http://us-west-2.ec2.archive.ubuntu.com/ubuntu/ trusty-backports main restricted universe multiverse" | tee /etc/apt/sources.list.d/multiverse.list
RUN echo ttf-mscorefonts-installer msttcorefonts/accepted-mscorefonts-eula select true |  debconf-set-selections
RUN ["apt-get", "-y", "install", "ttf-mscorefonts-installer"]
RUN dpkg-reconfigure  ttf-mscorefonts-installer
RUN apt-get install -y apt-transport-https apt-utils
RUN apt-get install  --reinstall -y ttf-mscorefonts-installer

CMD ["catalina.sh", "run"]
