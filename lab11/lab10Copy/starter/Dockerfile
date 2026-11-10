FROM eclipse-temurin:25-jre
WORKDIR /app

COPY target/gradebook-1.0-SNAPSHOT.jar app.jar
COPY data/ ./data/

ENTRYPOINT ["java", "-jar", "app.jar"]