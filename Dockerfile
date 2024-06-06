FROM openjdk:17-alpine
WORKDIR /usr/src/app
COPY . .
RUN ./mvnw install
EXPOSE 8080
CMD ["java", "-jar", "target/spot-dengue-0.0.1-SNAPSHOT.jar"]
