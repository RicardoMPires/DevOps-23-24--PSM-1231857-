# First stage: Build the application
FROM gradle:jdk17 as builder
LABEL author="Ricardo Pires"

WORKDIR /ca4-part1


COPY ./gradle_basic_demo .

RUN ./gradlew clean build


# Second stage: Create the final image
FROM openjdk:17-jdk-slim

WORKDIR /ca4-part1

# Copy the built JAR file from the first stage
COPY --from=builder /ca4-part1/build/libs/*.jar ca4-part1.jar

# Specify the command to run the application
ENTRYPOINT ["java", "-cp", "ca4-part1.jar", "basic_demo.ChatServerApp", "59001"]