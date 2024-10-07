# Gradle 7.6.1 JDK 8
FROM gradle:7.6.1-jdk8 as build
COPY --chown=gradle:gradle . /project/src
WORKDIR /project/src
RUN gradle build

# JDK 8
FROM openjdk:8

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Create dir /app
RUN mkdir /app

# Copy jar from build to /app dir
COPY --from=build /project/src/build/libs/java-springframework-1.0.0.jar /app/app.jar

# java -jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]