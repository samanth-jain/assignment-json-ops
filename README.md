# JSON Group Spring Boot Application

## Overview
This Spring Boot application allows you to insert and query JSON records in dynamic datasets using MongoDB. It supports group-by and sort-by operations via REST APIs.

## Features
- Insert JSON records into named datasets
- Query records with dynamic group-by and sort-by
- Robust error handling

## Prerequisites
- Java 17+
- Maven
- MongoDB (running locally or accessible remotely)

## Setup
1. **Clone the repository:**
   ```bash
   git clone <your-repo-url>
   cd jsonGroup
   ```
2. **Configure MongoDB:**
   Edit `src/main/resources/application.properties` if needed:
   ```properties
   spring.data.mongodb.uri=mongodb://localhost:27017/jsonGroup
   spring.data.mongodb.database=jsonGroup
   server.port=8080
   ```
3. **Run the application:**
   ```bash
   ./mvnw spring-boot:run
   # or
   mvn spring-boot:run
   ```

## API Usage

### Insert Record
- **POST** `/api/dataset/{datasetName}/record`
- **Body:**
  ```json
  {
    "id": 1,
    "name": "John Doe",
    "age": 30,
    "department": "Engineering"
  }
  ```
- **Response:**
  ```json
  {
    "message": "Record added successfully",
    "dataset": "employee_dataset",
    "recordId": 1
  }
  ```

### Query with Group-By
- **GET** `/api/dataset/{datasetName}/query?groupBy=department`
- **Response:**
  ```json
  {
    "groupedRecords": {
      "Engineering": [
        { "id": 1, "name": "John Doe", "age": 30, "department": "Engineering" }
      ]
    }
  }
  ```

### Query with Sort-By
- **GET** `/api/dataset/{datasetName}/query?sortBy=age&order=asc`
- **Response:**
  ```json
  {
    "sortedRecords": [
      { "id": 1, "name": "John Doe", "age": 30, "department": "Engineering" }
    ]
  }
  ```


## Testing
JUnit tests are provided for service and controller layers. Run:
```bash
mvn test
``` 