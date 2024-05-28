# Use the official Java 21 slim image from the OpenJDK project
FROM azul/zulu-openjdk:21-jre-latest

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file into the container
COPY target/popquiz*.jar /app/popquiz.jar

# Expose the port that the application will run on
EXPOSE 8080

# Set the command to run the application
CMD ["java", "-jar", "/app/your-spring-boot-app.jar"]