FROM bellsoft/liberica-openjdk-alpine:17.0.8

#workspace
WORKDIR /home/oleh/selenium-docker

# Add the required files
ADD target/docker-resources ./

# Run the tests
