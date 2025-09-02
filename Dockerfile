# Build
FROM maven:3.9.9-eclipse-temurin-17 AS builder

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn package -DskipTests -B


FROM maven:3.9.9-eclipse-temurin-17 AS tester

WORKDIR /app

COPY --from=builder /app /app

RUN mvn test


# Runtime
FROM eclipse-temurin:17-jre-alpine AS runtime

WORKDIR /app

RUN addgroup --system spring && adduser --system spring --ingroup spring && mkdir -p /var/log/dice && chown -R spring:spring /var/log/dice

USER spring:spring

COPY --from=builder /app/target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]
