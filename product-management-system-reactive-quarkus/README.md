# Product Management System

## Overview
The Product Management System is a Quarkus-based Java application designed to manage product details. It provides CRUD (Create, Read, Update, Delete) operations for product management and uses a MySQL database for persistence.

## Technologies Used
- **Java 8**
- **Quarkus Framework**
- **JUnit** for unit testing
- **MySQL** database

## Prerequisites
Ensure you have the following installed on your local machine:

1. Java 8 JDK
2. Maven (for building the application)
3. MySQL database
4. Docker (optional, for containerized deployment)
5. Git (optional, for cloning the repository)

## Setup and Run

### 1. Clone the Repository
Clone the project repository to your local machine or download the project ZIP.
```bash
$ git clone <repository_url>
$ cd product-management-system
```

### 2. Configure the Database
1. Start your MySQL server.
2. Create a database named `productdatabase`.
```sql command
CREATE DATABASE productdatabase;
```
3. Update the `application.properties` 
   file located at `src/main/resources/application.properties` with your MySQL connection details:
```properties
quarkus.datasource.url=jdbc:mysql://localhost:3306/productdatabase
quarkus.datasource.username=<your_mysql_username>
quarkus.datasource.password=<your_mysql_password>
```

### 3. Import Sample Data
The project includes an `import.sql` file that contains sample data. Ensure this file is located in `src/main/resources/`. Quarkus will automatically load it when the application starts.

### 4. Build the Application
Run the following command to build the application using Maven:
```bash
$ mvn clean package
```

### 5. Run the Application
Start the application in development mode:
```bash
$ mvn quarkus:dev
```
The application will start on [http://localhost:8080](http://localhost:8080).

### 6. Testing
Run the test cases using:
```bash
$ mvn test
```

### 7. Access the Application
The following endpoints are available for managing products:

- **GET /products**: Retrieve all products.
- **GET /products/{id}**: Retrieve a product by ID.
- **POST /products**: Create a new product.
- **PUT /products/{id}**: Update an existing product.
- **DELETE /products/{id}**: Delete a product by ID.

Use a tool like Postman or `curl` to interact with these endpoints.

## Optional: Run with Docker
1. Build the Docker image:
```bash
$ docker build -f src/main/docker/Dockerfile.jvm -t product-management-system .
```

2. Run the Docker container:
```bash
$ docker run -i --rm -p 8080:8080 product-management-system
```

## Troubleshooting
- Ensure the database is running and accessible.
- Check the logs for any error messages.
- Verify the `application.properties` configuration.

## License
This project is licensed under the MIT License.


# product-management-system

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/product-management-system-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Related Guides

- REST ([guide](https://quarkus.io/guides/rest)): A Jakarta REST implementation utilizing build time processing and Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it.
- Hibernate Validator ([guide](https://quarkus.io/guides/validation)): Validate object properties (field, getter) and method parameters for your beans (REST, CDI, Jakarta Persistence)
- RESTEasy Classic JSON-B ([guide](https://quarkus.io/guides/rest-json)): JSON-B serialization support for RESTEasy Classic
- REST Jackson ([guide](https://quarkus.io/guides/rest#json-serialisation)): Jackson serialization support for Quarkus REST. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it
- Hibernate ORM with Panache ([guide](https://quarkus.io/guides/hibernate-orm-panache)): Simplify your persistence code for Hibernate ORM via the active record or the repository pattern
- JDBC Driver - MySQL ([guide](https://quarkus.io/guides/datasource)): Connect to the MySQL database via JDBC
- REST JAXB ([guide](https://quarkus.io/guides/resteasy-reactive#xml-serialisation)): JAXB serialization support for Quarkus REST. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it.

## Provided Code

### Hibernate ORM

Create your first JPA entity

[Related guide section...](https://quarkus.io/guides/hibernate-orm)

[Related Hibernate with Panache section...](https://quarkus.io/guides/hibernate-orm-panache)


### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)

### RESTEasy JAX-RS

Easily start your RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started#the-jax-rs-resources)
