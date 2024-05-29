FROM openjdk:21-jdk

# Working directory inside the container
WORKDIR /app

# Copy the JAR file from the local machine to the working directory inside the container
COPY target/gymcrm-0.0.1-SNAPSHOT.jar /app/gymcrm-0.0.1-SNAPSHOT.jar

# Make ports available to the world outside this container
EXPOSE 5433 61616 8161

# Run the application
ENTRYPOINT ["java", "-jar", "gymcrm-0.0.1-SNAPSHOT.jar"]