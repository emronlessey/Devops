FROM amazoncorretto:17

WORKDIR /app

COPY target/SEMCode-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]