# ==============================================================================
# Stage 1: Build Stage (Full JDK + Maven)
# ==============================================================================
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /build

# Copy Maven POM and pre-fetch dependencies for efficient Docker layer caching
COPY pom.xml .
RUN mvn -B -q dependency:go-offline

# Copy application source code
COPY src ./src

# Build versioned executable JAR
ARG APP_VERSION=1.0.0
RUN mvn -B -q -DskipTests -Drevision=${APP_VERSION} clean package \
    && cp target/employee-directory-*.jar /build/app.jar

# ==============================================================================
# Stage 2: Runtime Stage (Lightweight JRE + Non-root user)
# ==============================================================================
FROM eclipse-temurin:17-jre
WORKDIR /app

# Create unprivileged system group & user for container security
RUN useradd --system --user-group appuser

# Copy the compiled JAR artifact from the build stage with user ownership
COPY --from=build --chown=appuser:appuser /build/app.jar /app/app.jar

# Switch execution to non-root user
USER appuser

# Expose Spring Boot web port
EXPOSE 8080

# Configure container entrypoint
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
