## Overview
This project is a RESTful API for managing a To-Do list within a SaaS application designed for companies. The API is built using Java and Spring framework and includes three types of users: Standard, Company-Admin, and Super Users. Each user type has different levels of access to tasks.

## Features

- **Standard Users**: Can access their own tasks.
- **Company-Admin Users**: Can access their own tasks and the tasks of their company employees.
- **Super Users**: Have access to tasks of all users.
- 
## Installation and Setup

### Prerequisites

- Java 11 or higher
- Maven
- Git

### Steps to Set Up

1. **Clone the Repository**:
    ```bash
    https://github.com/Yusufcandir/ToDo-list.git
    cd todo-list
    ```

2. **Build the Project**:
    ```bash
    mvn clean install
    ```

3. **Run the Application**:
    ```bash
    mvn spring-boot:run
    ```

### Testing

To run the tests, execute the following command:
```bash
mvn test
