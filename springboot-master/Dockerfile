# Use an official Maven image as the base image
FROM maven:3.8.4-openjdk-17-slim AS build

# Set the working directory in the container
WORKDIR /usr/src/app

# Copy the Maven project files to the container
COPY . .

# Make mvnw executable
RUN chmod +x mvnw

# Build the Maven project
RUN mvn clean install

# Specify the default command to run your application
CMD ["./mvnw", "spring-boot:run"]
