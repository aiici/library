FROM java:8-jre
ADD target/*.jar /app/backend.jar
EXPOSE 8080
CMD ["java", "-Xmx200m", "-jar", "/app/backend.jar"]
