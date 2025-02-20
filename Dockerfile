# Gradle 7.6.1 JDK 8
FROM gradle:7.6.1-jdk8 as build
COPY --chown=gradle:gradle . /project/src
WORKDIR /project/src
RUN gradle build

# Install Arial font
FROM debian:latest as fonts

RUN echo "deb http://deb.debian.org/debian bookworm contrib non-free" > /etc/apt/sources.list.d/contrib.list
RUN apt-get update
RUN apt-get install -y ttf-mscorefonts-installer
RUN apt-get clean
RUN rm -rf /var/lib/apt/lists/*

# JDK 8
FROM openjdk:8

# Copy fonts from the fonts stage
COPY --from=fonts /usr/share/fonts /usr/share/fonts

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Create dir /app
RUN mkdir /app

# Copy jar from build to /app dir
COPY --from=build /project/src/build/libs/springframework-1.0.0.jar /app/app.jar

# java -Xms256m -Xmx256m -jar /app/app.jar
ENTRYPOINT ["java","-Xms256m", "-Xmx256m", "-jar","/app/app.jar"]