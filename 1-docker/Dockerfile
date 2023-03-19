FROM ubuntu:22.04

ENV TZ=Europe/Warsaw

RUN apt-get update \
    && apt-get -y install \
    python3.9 \
    openjdk-8-jdk

ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY . $APP_HOME

RUN ./gradlew clean build

CMD ["./gradlew", "run"]
