# Start from a base image with Java installed
FROM openjdk:17-jdk-slim

# Set a working directory
WORKDIR /app

# Copy your application into the Docker image
COPY ./CA2/Part2/gradle_basic_demo .

# Run the Gradle assemble task to compile your application and produce the WAR file
RUN ./gradlew assemble

# Expose the necessary port for your application
EXPOSE 8080

# Specify the command to run your application when the Docker container is started
CMD ["java", "-jar", "./build/libs/react-and-spring-data-rest-basic-0.0.1-SNAPSHOT.war"]