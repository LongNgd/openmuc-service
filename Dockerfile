# ====== Build Stage ======
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom.xml để cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code vào
COPY src ./src

# Build file .jar, bỏ qua test để nhanh hơn
RUN mvn clean package -DskipTests


# ====== Runtime Stage ======
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy file jar từ stage build
COPY --from=build /app/target/iot-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

# JVM tối ưu cho Docker
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Dfile.encoding=UTF-8"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]