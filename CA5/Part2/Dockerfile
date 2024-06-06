# Start from a base image with Tomcat installed
FROM tomcat:9.0-jdk17-openjdk-slim

# Remove the default Tomcat webapps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy your WAR file into the Docker image
COPY ./react-and-spring-data-rest-basic-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/

# Expose the necessary port for your application
EXPOSE 8080