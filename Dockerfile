
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/cgpa-calculator.jar app.jar

EXPOSE 8081

CMD ["java", "--add-modules", "jdk.httpserver", "-cp", "app.jar", "com.demo.WebApp"]
