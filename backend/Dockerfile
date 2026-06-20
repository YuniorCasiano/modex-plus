# ============================================================
# Dockerfile - Modex Monolito
# Build multi-stage: primero compila con Maven + JDK 21,
# luego copia solo el JAR final a una imagen liviana de JRE.
# Esto hace que la imagen final sea mucho mas pequena y
# arranque mas rapido en Render.
# ============================================================

# ── ETAPA 1: build ──────────────────────────────────────────
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copiamos primero solo el pom.xml para aprovechar el cache
# de capas de Docker - si no cambian las dependencias, Docker
# no las vuelve a descargar en el siguiente build
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Ahora copiamos el codigo fuente y compilamos
COPY src ./src
RUN mvn clean package -DskipTests -B

# ── ETAPA 2: imagen final liviana ───────────────────────────
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copiamos solo el JAR ejecutable generado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Render asigna el puerto via la variable de entorno PORT
EXPOSE 8080

# Le decimos a Spring Boot que use el perfil "prod"
ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java", "-jar", "app.jar"]
