FROM amazoncorretto:17

WORKDIR /app

COPY target/SEMCode-0.1.0.4-jar-with-dependencies.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]