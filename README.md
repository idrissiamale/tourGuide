# TourGuide
Back end of the application which has been refactored into microservices with Java and Spring Boot framework. TourGuide is an interactive guide that allow users to see the nearby tourist attractions. They also receive rewards for visiting them.

## Getting Started
### Prerequisites

A list of technologies you need in order to install TourGuide :

- Java 1.8
- Gradle 4.8.1
- Spring Boot 2.1.6 RELEASE
- Eureka 1.4.7 RELEASE
- Feign springCloudVersion: Greenwich.SR6
- Docker 4.1.1
- Jacoco 0.8.4

### Installing

A step by step series of examples that tell you how to get a development environment running:

1.Install Java:

https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html

2.Install Gradle:

https://docs.gradle.org/4.8.1/userguide/userguide.html

3.Install Spring Boot:

https://spring.io/guides/gs/spring-boot/ : a step by step tutorial.

https://start.spring.io : please select Spring Web dependency before installing Spring Boot.

4.Install Eureka Server:

https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-eureka-server

5.Install Feign:

https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-openfeign

6.Install Docker:

https://docs.docker.com/desktop/

7.Install Jacoco:

https://mvnrepository.com/artifact/org.jacoco/org.jacoco.agent


### Running App

Import the code into an IDE of your choice and run TourGuide microservices with the command below :

   `./gradlew bootRun`
  
But before launching other microservices, please make sure to run Eureka Server first.
You can go to http://localhost:7070 to view the Eureka dashboard.


### Testing

The application has unit and integration tests. To run them, please execute the Gradle command below:

`./gradlew test`

Please use JaCoCo for the code coverage. To run it, please use the command below:

`./gradlew codeCoverageReport`

### Containerization with Docker

To run TourGuide container, please execute the command below:

`docker-compose up`