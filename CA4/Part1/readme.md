# Class Assignment 4 Part 1

## Introduction

The goal of this class assignment was to use Docker to create an image and run a container with that image. In this
assignment 2 dockerfiles were created, with two different version of the same app.
The final result of the assignment can be found [here](https://github.com/RicardoMPires/DevOps-23-24--PSM-1231857-).

## Table of Contents

1. [Version 1](#Version-1)
2. [Version 2](#Version-2)
3. [Final Remarks](#Final-Remarks)

## Version 1

1. For this version of the assignment, the only requirement is to create a dockerfile, as we will be cloning a
   repository with the project already implemented, build it and then run the application.
   Before running the app we need to change the permission of the gradlew file to make it executable, then we can build
   the app and run it, which will start a chat server on port 59001.
   The dockerfile created for this version is the following:

```dockerfile
# First stage: Build the application
FROM gradle:jdk17 as builder
LABEL author="Ricardo Pires"

WORKDIR /ca4-part1


RUN git clone https://bitbucket.org/pssmatos/gradle_basic_demo.git


WORKDIR /ca4-part1/gradle_basic_demo
RUN chmod +x gradlew

RUN ./gradlew build

# Second stage: Create the final image
FROM openjdk:17-jdk-slim

WORKDIR /ca4-part1


COPY --from=builder /ca4-part1/gradle_basic_demo/build/libs/*.jar ca4-part1.jar


ENTRYPOINT ["java", "-cp", "ca4-part1.jar", "basic_demo.ChatServerApp", "59001"]
```

2. After the chat server is running, navigate to the folder where the app is located and run the following command to
   start the chat client:

```bash
./gradlew runclient
```

3. The chat client will start and you can start chatting with other users connected to the chat server. When the server
   is started it will display the following message:

```bash
The chat server is running...
```

4. The chat client will display the following message when a user connects to the server:

```bash
INFO  basic_demo.ChatServer.Handler - A new user has joined: <username>
```

5. The chat client will display the following message when a user leaves the server:

```bash
INFO  basic_demo.ChatServer.Handler - <username> has left the chat
```

6. Push the image to Docker Hub by running the following command:

```bash
docker tag ca4-part1:Version1 ricardompires/devops_23_24:Version1
```

```bash
docker push ricardompires/devops_23_24:CA4_Part1_Version1
```

## Version 2

For this version of the assignment, we will be copying the contents of the folder where the Dockerfile is located and
extract the .jar file to run the server. To do so, first we must copy the contents from CA2 Part1 to the same folder as
the Dockerfile:

1. Navigate to the CA4/Part1/Version2 folder and run the following command:

```bash
cp -r ../../../CA2/Part1/gradle_basic_demo .
```

2. Afterwards we can create the dockerfile and instruct it to copy the files and run the server. The dockerfile created
   for this version is the following:

```dockerfile
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
```

*NOTE:* Because the CA2 Part1 project was compiled with Java 21 and the Dockerfile specifies a Java 17 slim image, a
clean build is required to compile the project with the correct Java version, otherwise the clean build command would
not be required.

3. Push the image to Docker Hub by running the following command:

```bash
docker tag ca4-part1:Version2 ricardompires/devops_23_24:CA4_Part1_Version2
```

```bash
docker push ricardompires/devops_23_24:Version1
```

## Final Remarks

Both Dockerfiles are very similar and the main difference is the way the project is cloned and built. In the first
version the project is cloned from a repository and built, while in the second version the project is copied from the
local machine and built. The final result is the same, a chat server running on port 59001. The chat client can be
started by running the following command:

```bash
./gradlew runclient
```

A Java 17 slim image was used in the Docker image in order to keep them as light as possible.
After both images were created, they were pushed to Docker Hub and can be
found [here](https://hub.docker.com/repository/docker/ricardompires/devops_23_24/general).



