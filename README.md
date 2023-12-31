# Repository Info Provider

## Usage
###  Run with Docker

To run this app You should use `git clone` command or download zip file with this project and extract it. After that open Command Line at project directory and build application using this command:
```bash
mvn clean package
```

Next build Docker image using this command:
```
docker build -t repo_info_provider_image .
```

Then run Docker container in interactive mode and include mapping port 8080 in Your container to port in Your machine:
```
docker run --name repo_info_provider -it -p 8080:8080 repo_info_provider_image 
```

To exit the created container use CTRL+C in interactive view in terminal.

## API Endpoints

#### Get repo info

```http
   GET /api/repositories/{owner}/{repositoryName}
```

| Parameter        | Type     | Description                                  |
|:-----------------|:---------|:---------------------------------------------|
| `owner`          | `String` | **Required**. Owner of the GitHub repository |
| `repositoryName` | `String` | **Required**. Name of the GitHub repository  |


## Demo
#### 1. Get repo info
###### Request
```http
   GET localhost:8080/api/repositories/Control11/ticket-booking-app
```
###### Respond
```json
{
    "fullName": "Control11/ticket-booking-app",
    "description": "This application represents basic seat booking system for movies in cinema.",
    "cloneUrL": "https://github.com/Control11/ticket-booking-app.git",
    "stars": 0,
    "createdAt": "2023-07-06T16:25:16Z"
}
```
<br>

#### 2. Exception handling - Error 404 Not found
###### Request (with non-existent user and repo)
```http
   GET localhost:8080/api/repositories/randomOwner/random-repo
```
###### Respond
```json
{
    "status": 404,
    "error": "Not Found",
    "message": "Requested repository could not be found",
    "timestamp": "2023-08-20T12:34:16.5021904Z"
}
```


## Requirements and used tools
Before any step make sure You have installed Docker on Your machine.

- Docker
- Java 17
- Spring-Boot 3.1.2
- Maven 3.8.6

