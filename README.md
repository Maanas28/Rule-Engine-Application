# Rule Engine Application

## Overview

The Zeotap Rule Engine Application is a Spring Boot project designed to create, combine, and evaluate rules using a rule engine. This application provides RESTful endpoints for interacting with the rule engine, allowing users to manage and evaluate complex rules.

## Features

- **Create Rule**: Create a new rule from a provided rule string.
- **Combine Rules**: Combine multiple rules into a single rule.
- **Evaluate Rule**: Evaluate a rule against provided data to determine its result.

## Project Structure

```
ZeotapAssignmentApplication-1-main
├── .mvn
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           └── ruleengine
│   │   │               └── ProjectApplication1
│   │   │                   ├── controller
│   │   │                   │   └── RuleController.java
│   │   │                   ├── model
│   │   │                   ├── repository
│   │   │                   └── service
│   └── test
├── .gitattributes
├── .gitignore
├── README.md
├── mvnw
├── mvnw.cmd
└── pom.xml
```

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven
- Git

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/ZeotapAssignmentApplication-1-main.git
   ```
2. Navigate to the project directory:
   ```bash
   cd ZeotapAssignmentApplication-1-main
   ```
3. Build the project using Maven:
   ```bash
   mvn clean install
   ```

### Running the Application

1. Start the application:
   ```bash
   mvn spring-boot:run
   ```
2. The application will be running on `http://localhost:8080`.

### Endpoints

- **Create Rule**
  - **URL**: `/rules/create`
  - **Method**: `POST`
  - **Request Body**: `String` (rule string)
  - **Response**: `Node`

- **Combine Rules**
  - **URL**: `/rules/combine`
  - **Method**: `POST`
  - **Request Body**: `List<String>` (list of rule strings)
  - **Response**: `Node`

- **Evaluate Rule**
  - **URL**: `/rules/evaluate`
  - **Method**: `POST`
  - **Request Body**: `RequestData` (containing `astId` and `data`)
  - **Response**: `Map<String, Boolean>`

## Example Usage

### Create Rule

```bash
curl -X POST http://localhost:8080/rules/create -H "Content-Type: application/json" -d "\"your rule string\""
```

### Combine Rules

```bash
curl -X POST http://localhost:8080/rules/combine -H "Content-Type: application/json" -d '["rule string 1", "rule string 2"]'
```

### Evaluate Rule

```bash
curl -X POST http://localhost:8080/rules/evaluate -H "Content-Type: application/json" -d '{"astId": "1", "data": {"key": "value"}}'
```

## Contributing

1. Fork the repository
2. Create a new branch (`git checkout -b feature-branch`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin feature-branch`)
5. Create a new Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgements

- Thanks to the Spring Boot community for their invaluable documentation and support.
