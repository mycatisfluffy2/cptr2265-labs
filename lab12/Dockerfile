# syntax=docker/dockerfile:1

# ---------- Stage 1: fetch runtime dependencies (PostgreSQL JDBC) ----------
FROM maven:3.9-eclipse-temurin-17 AS deps
WORKDIR /build
COPY pom.xml ./
RUN mvn -B -q dependency:copy-dependencies -DincludeScope=runtime -DoutputDirectory=/app/lib \
  && mv /app/lib/postgresql-*.jar /app/lib/postgresql.jar

# ---------- Stage 2: compile the application ----------
FROM eclipse-temurin:17-jdk AS build
WORKDIR /build
COPY --from=deps /app/lib /app/lib
COPY NotesLogsApp.java AppServer.java ./
# RUN javac -encoding UTF-8 -cp /app/lib/postgresql.jar -d /app/classes NotesLogsApp.java AppServer.java \
#  && jar --create --file /app/app.jar --main-class AppServer --class-path lib/postgresql.jar -C /app/classes .
RUN javac -encoding UTF-8 -cp /app/lib/postgresql.jar -d /app/classes NotesLogsApp.java AppServer.java \
  && printf 'Manifest-Version: 1.0\nMain-Class: AppServer\nClass-Path: lib/postgresql.jar\n\n' > /app/MANIFEST.MF \
  && jar --create --file /app/app.jar --manifest /app/MANIFEST.MF -C /app/classes .


# ---------- Stage 3: slim runtime ----------
FROM eclipse-temurin:17-jre
ENV PORT=8080 \
  JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75"
RUN apt-get update \
  && apt-get install -y --no-install-recommends curl \
  && rm -rf /var/lib/apt/lists/* \
  && groupadd -r app && useradd -r -g app -d /app app
WORKDIR /app
COPY --from=build /app/app.jar /app/app.jar
COPY --from=build /app/lib /app/lib
RUN chown -R app:app /app
USER app
EXPOSE 8080
HEALTHCHECK --interval=15s --timeout=5s --start-period=30s --retries=3 \
  CMD curl -fsS "http://127.0.0.1:${PORT}/health" || exit 1
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
