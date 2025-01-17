# Use an official Maven image as the base
FROM maven:3.8.5-openjdk-11

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project files to the container
COPY . .

# Download dependencies and package the project (skipping tests initially for faster builds)
RUN mvn clean package -DskipTests

# Define the default command to run the tests
CMD ["mvn", "test"]
