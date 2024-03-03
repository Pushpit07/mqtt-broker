# Use an official Maven image as the base image
FROM maven:3.8.4-openjdk-17-slim AS build

# Set the working directory in the container
WORKDIR /usr/src/app

# Copy only the Maven project files needed for dependency resolution
COPY ./pom.xml .

# Download dependencies. This step will be cached if the pom.xml file hasn't changed.
RUN mvn dependency:go-offline

# Make mvnw executable
COPY ./mvnw .
RUN chmod +x mvnw

# Copy the rest of the project
COPY . .

# Build the Maven project
RUN mvn clean install

# Specify the default command to run your application
CMD ["./mvnw", "spring-boot:run"]