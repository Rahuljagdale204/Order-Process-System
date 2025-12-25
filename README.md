# orderProcessSystem

This application was generated using JHipster 8.6.0, you can find documentation and help at [https://www.jhipster.tech/documentation-archive/v8.6.0](https://www.jhipster.tech/documentation-archive/v8.6.0).

## mini order Processing System :

1.  Exposes REST APIs to create and retrieve orders
2.  Stores orders using in-memory data structures
3.  Processes orders asynchronously using Apache Camel, files, and ActiveMQ

The system demonstrates **REST API handling, in-memory persistence, file-based integration, message queuing, asynchronous processing, JWT-based authentication and Authorization**.

---

## 🚀 Tech Stack

- **Java**: 17
- **Spring Boot** (via JHipster 8.11.0)
- **Apache Camel**
- **ActiveMQ (Classic)**
- **H2 In-Memory Database**
- **Maven**
- **JWT Authentication**

---

## 🏗 High-Level Architecture

Client (REST)  
 |  
 | POST /api/orders  
 v  
 Spring Boot Application  
 |  
 | In-memory storage  
 |  
 | (Bonus) Write Order JSON  
 v  
 File System (/input/orders)  
 |  
 | Apache Camel Route  
 v  
 ActiveMQ Queue (ORDER.CREATED.QUEUE)  
 |  
 | Apache Camel Consumer  
 v  
 Application Logs

---

## 📌 Features Implemented

### Core Features

- Create Order via REST API
- Persist order in **H2 in-memory database**
- Fetch orders by `customerId`

### Bonus Features (Asynchronous Processing)

- Write order details to JSON file
- Apache Camel route to:
  - Poll order files
  - Validate order data
  - Publish valid orders to ActiveMQ queue
- Apache Camel consumer to:
  - Consume messages from queue
  - Log order details asynchronously

---

## 📂 Important Directories

- input/orders/ → Generated order JSON files
- error/orders/ → Invalid order files (if any)
- src/main/java/... → Application source code

---

## 🔧 Server & Runtime Requirements

| Requirement | Version                  |
| ----------- | ------------------------ |
| Java        | 17                       |
| Maven       | 3.8+                     |
| Node.js     | Required for JHipster UI |
| ActiveMQ    | Classic (5.x)            |

---

## 🧪 Database Configuration

- **Database**: H2 (In-Memory)
- **Persistence**: Active only during application runtime
- **No external DB setup required**

---

## 📨 ActiveMQ Configuration

- **Broker**: ActiveMQ Classic
- **Queue Name**: ORDER.CREATED.QUEUE

- **Message Type**: JSON (TextMessage)
- **Security**: No `ObjectMessage` used

> Ensure ActiveMQ is running before starting the application.

---

## ▶️ Build & Run Instructions

### 1️⃣ Build the Project (Skip Tests)

```bash
mvn clean install -DskipTests
```

### 2️⃣ Run the Application

```bash
./mvnw  // Linux or mac
mvnw    // Windows
```

The application will start on:
`http://localhost:8080`

## 🔐 Authentication & Authorization

The application uses JWT-based authentication to secure all REST APIs.

### Authentication

    Authentication Type: **JWT**

    All order APIs are accessible only to authenticated users

    To obtain a JWT token, call the authentication API:
    ```bash
      curl --location 'http://localhost:8080/api/authenticate' \
        --header 'Content-Type: application/json' \
        --data '{
          "username": "admin",
          "password": "admin"
        }'
    ```

    The response contains an `id_token`.
    Use this token as a `Bearer Token` in Postman:

    Authorization → `Type: Bearer Token`

    Token: <id_token>

### Authorization

    Two roles are defined:
    > ADMIN
    > USER

    Access rules:
    ADMIN : Can create orders and view all orders
    USER : Can create orders and view only their own orders

    | Role | UserName | Password |
    |----------| -------- | -------- |
    | ADMIN | admin | admin |
    | USER | user | user |

    > ✔ All APIs are secured with JWT
    > ✔ Role-based access control is fully implemented

## Project Structure

Node is required for generation and recommended for development. `package.json` is always generated for a better development experience with prettier, commit hooks, scripts and so on.

In the project root, JHipster generates configuration files for tools like git, prettier, eslint, husky, and others that are well known and you can find references in the web.

`/src/*` structure follows default Java structure.

- `.yo-rc.json` - Yeoman configuration file
  JHipster configuration is stored in this file at `generator-jhipster` key. You may find `generator-jhipster-*` for specific blueprints configuration.
- `.yo-resolve` (optional) - Yeoman conflict resolver
  Allows to use a specific action when conflicts are found skipping prompts for files that matches a pattern. Each line should match `[pattern] [action]` with pattern been a [Minimatch](https://github.com/isaacs/minimatch#minimatch) pattern and action been one of skip (default if omitted) or force. Lines starting with `#` are considered comments and are ignored.
- `.jhipster/*.json` - JHipster entity configuration files

- `npmw` - wrapper to use locally installed npm.
  JHipster installs Node and npm locally using the build tool by default. This wrapper makes sure npm is installed locally and uses it avoiding some differences different versions can cause. By using `./npmw` instead of the traditional `npm` you can configure a Node-less environment to develop or test your application.

## Development

The build system will install automatically the recommended version of Node and npm.

We provide a wrapper to launch npm.
You will only need to run this command when dependencies change in [package.json](package.json).

```
./npmw install
```

We use npm scripts and [Webpack][] as our build system.

Run the following commands in two separate terminals to create a blissful development experience where your browser
auto-refreshes when files change on your hard drive.

```
./mvnw
./npmw start
```

## 📡 REST API Endpoints

### Create Order

```bash
  POST /api/orders
```

Payload

```json
{
  "customerId": "CUST1001",
  "product": "Laptop",
  "amount": 75000
}
```

### Fetch Orders by Customer

```bash
  GET /api/orders?customerId=CUST1001
```

## 🧾 Verified Execution Logs

The following logs confirm the successful end-to-end flow:

```pgsql
  Order saved successfully in DB
  Order file written to input/orders/order-1001.json
  Camel route picked up the file
  Order published to ActiveMQ queue
  Order consumed and processed asynchronously
```

Sample log excerpt:

```pgsql
  Processing file: order-1001.json
  Valid order | OrderId=1001
  Order processed | OrderId=1001 | CustomerId=CUST1001 | Amount=75000.0
```

## 📝 Notes

> No external database configuration required.

> Application is fully self-contained.

> Designed following best practices for security and messaging.
