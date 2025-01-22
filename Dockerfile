# Gradle 7.6.1 JDK 8
FROM gradle:7.6.1-jdk8 as build
COPY --chown=gradle:gradle . /project/src
WORKDIR /project/src
RUN gradle build

# JDK 8
FROM openjdk:8

# Install Arial font
RUN apt-get update && \
    apt-get install -y --no-install-recommends ttf-mscorefonts-installer && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Create dir /app
RUN mkdir /app

# Copy jar from build to /app dir
COPY --from=build /project/src/build/libs/springframework-1.0.0.jar /app/app.jar

# java -Xms256m -Xmx256m -jar /app/app.jar
ENTRYPOINT ["java","-Xms256m", "-Xmx256m", "-jar","/app/app.jar"]