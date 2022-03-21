FROM maven:3.8.3-openjdk-16-slim AS build
ARG PROFILE=default
WORKDIR /app
COPY pom.xml .
COPY src/ ./src/
RUN mvn -B package --file pom.xml -DskipTests -P${PROFILE}

FROM openjdk:16-slim
ARG PROFILE
ENV PROFILE=${PROFILE}
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8046
ENTRYPOINT java -jar app.jar --spring.profiles.active=$PROFILE