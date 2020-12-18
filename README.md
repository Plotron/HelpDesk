# Spring Framework MVC Example: HelpDesk Ticketing System 

A simple project demonstrating how **Spring Framework** can be used in conjunction with **Thymeleaf** to implement a simple HelpDesk web application.

## Features

* read, search, create, edit and delete technical support articles
* create and manage support tickets with a simple messaging system

## Setup
The steps below will get you up and running with a local development environment. We assume you have the following installed:

* JDK 1.8
* PostgreSQL
* Maven
* Lombok Plugin for your IDE

Create database
````sql
CREATE DATABASE "helpdesk"
````

Set environment variable
```bash
export 'JDBC_DATABASE_URL=jdbc:postgresql://localhost/helpdesk?user=[username]&password=[password]'
```

### Development
Package `com.pg.helpdesk.init` contains several database initializer beans that populate the database with example data. 
You can use the `ExampleDataLoaderInitializer` class to configure custom DataLoaders.

Example user credentials that you can use to log in can be found in the `UserInitializer` class.

Run application
```bash
mvn spring-boot:run
```

Configuration
-----
| Key               | Description                     |
|-------------------|---------------------------------|
| JDBC_DATABASE_URL | database connection information |

Demo
-----
![Screenshot](docs/images/helpdesk com home example.png)
-----
![Screenshot](docs/images/helpdesk com ticket example.png)