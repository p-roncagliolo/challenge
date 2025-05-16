# Directa24 Back-End Developer Challenge

## Overview
This project is a Spring Boot application that provides a REST API to fetch directors of movies released after the year 2010. The API allows users to retrieve a list of directors who have directed more than a specified number of movies.

## API Endpoint
The main endpoint for accessing the directors is:

```
GET /api/directors?threshold=<threshold>
```

### Parameters
- `threshold`: An integer value that specifies the minimum number of movies a director must have directed to be included in the response.

### Example Request
To get the list of directors who have directed more than 4 movies, you can make the following request:

```
/api/directors?threshold=4
```

### Example Response
The response will be in JSON format and will look like this:

```
{
  "directors": ["Martin Scorsese", "Woody Allen"]
}
```

## Project Structure
The project is structured as follows:

```
backend-dev-challenge
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── directa24
│   │   │           ├── BackendDevChallengeApplication.java
│   │   │           ├── controller
│   │   │           │   └── DirectorController.java
│   │   │           ├── service
│   │   │           │   └── DirectorService.java
│   │   │           └── model
│   │   │               └── Movie.java
│   │   └── resources
│   │       ├── application.properties
│   │       └── static
│   └── test
│       └── java
│           └── com
│               └── directa24
│                   └── BackendDevChallengeApplicationTests.java
├── pom.xml
└── README.md
```

## Setup Instructions
1. Clone the repository to your local machine.
2. Navigate to the project directory.
3. Build the project using Maven:
   ```
   mvn clean install
   ```
4. Run the application:
   ```
   mvn spring-boot:run
   ```
5. Access the API at `http://localhost:8080/api/directors?threshold=<threshold>`.

## Evaluation Criteria
- **Correctness**: Ensure the API correctly fetches and displays the movie data.
- **Code Quality**: Maintain well-structured and organized code.
- **Java Spring Best Practices**: Follow best practices and principles in Java Spring.
- **Bonus Points**: Implement additional features or best practices where applicable.

## Improvements
If there are any improvements or features that were not implemented, please describe them here and explain why they were left out.