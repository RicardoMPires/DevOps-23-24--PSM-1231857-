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
