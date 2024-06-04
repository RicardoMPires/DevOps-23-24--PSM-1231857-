# Class Assignment 4 Part 2

## Introduction

The goal of this class assignment was to use Docker to create two images and a docker-compos file and run a container
with those images. In this
assignment 2 dockerfiles and a dokcer-compose file were created, one image to run the web app and the second one to host
an H2 database.
The final result of the assignment can be found [here](https://github.com/RicardoMPires/DevOps-23-24--PSM-1231857-).

## Table of Contents

1. [Getting started](#Getting-started)
2. [Web Dockerfile](#Web-Dockerfile)
3. [Database Dockerfile](#Database-Dockerfile)
4. [Docker-compose file](#Docker-compose-file)
5. [Alternative Implementation solution](#Alternative-Implementation-solution)

## Getting started

To complete this assignment first we need to manually copy the contents of the CA2 Part2 project to the same folder as
the Dockerfile. This is so that the Dockerfile can copy the contents of the project and run the server.

## Web Dockerfile

The first Dockerfile created was the web Dockerfile. This Dockerfile is responsible for creating an image that runs the
app. It first copies the contents of the react-and-spring-data-rest-basic folder and then changes the permissions of the
gradlew file to make it executable to then build the project and run it. The Dockerfile is shown below:

```Dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY ./react-and-spring-data-rest-basic .
WORKDIR /app
RUN chmod +x gradlew
CMD ["./gradlew", "build"]
EXPOSE 8080
RUN ls -la
CMD ["java", "-jar", "dist/react-and-spring-data-rest-basic-0.0.1-SNAPSHOT.jar"]
```

## Database Dockerfile

This Dockerfile is responsible for creating an image that runs the H2 database. It first copies the contents of the h2
folder and then runs the H2 database. The Dockerfile is shown below:

```Dockerfile
FROM ubuntu:focal

RUN apt-get update && \
  apt-get install -y wget openjdk-17-jdk-headless && \
   rm -rf /var/lib/apt/lists/* \

WORKDIR /opt/h2
RUN wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar -O h2.jar
EXPOSE 8082
EXPOSE 9092
CMD ["java", "-cp", "h2.jar", "org.h2.tools.Server", "-ifNotExists", "-web", "-webAllowOthers", "-webPort", "8082", "-tcp", "-tcpAllowOthers", "-tcpPort", "9092"]
```

## Docker-compose file

This file is responsible for creating the two images and running the containers. The file is shown below:

```yaml
services:
  db:
    build:
      context: .
      dockerfile: Dockerfile-db
    container_name: CA4_Part2_db
    ports:
      - "8082:8082"
      - "9092:9092"
    volumes:
      - h2-data:/opt/h2-data
    networks:
      CA4_network:
        ipv4_address: 192.168.33.11

  web:
    build:
      context: .
      dockerfile: Dockerfile-web
    container_name: CA4_Part2_web
    ports:
      - "8080:8080"
    networks:
      CA4_network:
        ipv4_address: 192.168.33.10
    depends_on:
      - "db"

volumes:
  h2-data:
    driver: local

networks:
  CA4_network:
    ipam:
      driver: default
      config:
        - subnet: 192.168.33.0/24
```

After the docker-compose file is created we can build the images by running the following command:

```bash
docker-compose build
```

Push the Web docker image to Docker Hub by running the following command:

```bash
docker tag ca4-part2:web ricardompires/devops_23_24:CA4_Part2_web
```

```bash
docker push ricardompires/devops_23_24:CA4_Part2_web
```

And finally push the Database docker image to Docker Hub by running the following command:

```bash
docker tag ca4-part2:db ricardompires/devops_23_24:CA4_Part2_db
```

```bash
docker push ricardompires/devops_23_24:CA4_Part2_db
```

After building the images, the containers running them can be started together by running the following command:

```bash
docker-compose up
```

*Note:* It is also possible to run each container separately by running the following command:

```bash
docker-compose up db
```

```bash
docker-compose up web
```

## Alternative Implementation solution

An alternative solution for this assignment would be to deploy the app and the database in a Kubernetes cluster. This
would allow for better scalability and availability of the app. The app could be deployed in a pod and the database in
another pod. The app would then connect to the database using the service name. This would allow for better separation
of concerns and better management of the app and the database.
After pushing the docker images to Docker Hub, we would need to create a Kubernetes deployment file for the app and the
database. The deployment file would look something like this:

Web app deployment file:

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: ca4-part2-web
spec:
  replicas: 1
  selector:
    matchLabels:
      app: ca4-part2-web
  template:
    metadata:
      labels:
        app: ca4-part2-web
    spec:
      containers:
        - name: ca4-part2-web
          image: ricardompires/devops_23_24:CA4_Part2_web
          ports:
            - containerPort: 8080

```

Web app service file:

```yaml
apiVersion: v1
kind: Service
metadata:
  name: ca4-part2-web
spec:
  type: NodePort
  ports:
    - port: 8080
      targetPort: 8080
      nodePort: 30080
  selector:
    app: ca4-part2-web
```

Database deployment file:

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: ca4-part2-db
spec:
  replicas: 1
  selector:
    matchLabels:
      app: ca4-part2-db
  template:
    metadata:
      labels:
        app: ca4-part2-db
    spec:
      containers:
        - name: ca4-part2-db
          image: ricardompires/devops_23_24:CA4_Part2_db
          ports:
            - containerPort: 8082
            - containerPort: 9092
```

Database service file:

```yaml
apiVersion: v1
kind: Service
metadata:
  name: ca4-part2-db
spec:
  type: NodePort
  ports:
    - port: 8082
      targetPort: 8082
      nodePort: 30082
    - port: 9092
      targetPort: 9092
      nodePort: 30092
  selector:
    app: ca4-part2-db
```

Unlike Docker, Kubernetes uses a declarative approach to manage the state of the cluster. This means that we define the
desired state of the cluster in a YAML file and Kubernetes will make sure that the cluster is in that state. This allows
for better management of the cluster and better scalability and availability of the app.
It is not uncommon for Docker and Kubernetes to be used together. Docker is used to build the images and Kubernetes is
used to deploy and manage the containers. This allows for better separation of concerns and better management of the app
and the database.

## Final Remarks

Although it would be possible in the Dockerfile to clone the repository and copy the contents from there, in order to
keep the image as light as possible and to improve performance I chose to copy the content manually as they were already
available locally.
After starting the containers with these images and accessing the IP specified in the Dockerfile, the web app should be
running and the database should be accessible through the H2 console.



