# Resale & Order Management API

## 📌 Prerequisites
To run this Spring Boot application, ensure you have the following installed:

### Required Software:
- **Java 17**
  - Download: [Oracle](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- **Maven 3.8+**
  - Download: [Apache Maven](https://maven.apache.org/download.cgi)
- **Git (optional)**
  - Download: [Git SCM](https://git-scm.com/downloads)

## 🚀 Build & Run the Application

### 1️⃣ Clone the Repository
```sh
git clone https://github.com/LucasGeovanni/desafio-tech.git
cd desafio-tech
```

### 2️⃣ Build the Project with Maven
```sh
mvn clean package
```

### 3️⃣ Run the Application
```sh
java -jar target/desafio-tech-0.0.1-SNAPSHOT.jar
```
By default, the application will start on port **8080**.

## 🌍 Available Endpoints

### 📌 Resale Endpoints
| Method | Endpoint | Description |
|--------|---------|-------------|
| `POST` | `/api/v1/resale` | Create a new resale |
| `GET`  | `/api/v1/resale` | Retrieve all resales |
| `GET`  | `/api/v1/resale/{uuid}` | Retrieve resale by UUID |
| `GET`  | `/api/v1/resale/{uuid}/control` | Retrieve integration control for a resale |
| `POST` | `/api/v1/resale/control/reprocess` | Reprocess a resale order |

### 📌 Order Endpoints
| Method | Endpoint | Description |
|--------|---------|-------------|
| `POST` | `/api/v1/orders/{resaleUuid}` | Create an order for a resale |
| `GET`  | `/api/v1/orders/{uuid}` | Retrieve order by UUID |
| `GET`  | `/api/v1/orders/resale/{resaleUuid}` | Retrieve all orders for a resale |

## 🛠️ Configuration
By default, the application loads configurations from `application.properties`. You can override properties using:
```sh
java -jar target/desafio-tech-0.0.1-SNAPSHOT.jar --spring.config.location=classpath:/application-test.properties
```
## 🔍 API Documentation (Swagger UI)
Once the application is running, you can access the API documentation via Swagger UI:
```sh
http://localhost:8080/swagger-ui.html
```
This interface allows you to explore and test the available endpoints.

## 🧪 Running Tests
To execute tests, use:
```sh
mvn test
```
