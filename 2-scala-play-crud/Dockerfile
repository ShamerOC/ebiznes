FROM sbtscala/scala-sbt:eclipse-temurin-focal-11.0.17_8_1.8.2_2.13.10

ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY . $APP_HOME

RUN sbt clean compile

CMD ["sbt", "run"]

EXPOSE 9000/tcp
