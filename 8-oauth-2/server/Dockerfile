FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY gradle/ ./gradle/
COPY gradlew build.gradle.kts settings.gradle.kts ./

COPY src ./src

RUN ./gradlew clean build

CMD ["java", "-jar", "./build/libs/server-0.0.1-SNAPSHOT.jar"]


