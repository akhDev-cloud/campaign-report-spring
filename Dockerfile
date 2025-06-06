FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY ./target/campaign-report-0.0.1-SNAPSHOT.jar ./campaign-report.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/campaign-report.jar"]