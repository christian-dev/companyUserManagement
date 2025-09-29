# Project companyUserManagement
## Description
The purpose of this application is to expose services for the management of user in a company.
These service are mainly :
* Register an user
* Display details of a registered users

## Frameworks and technologies
* Java 8
* Spring boot version 2.7.2
* Spring Data 
* Spring core version 5.3.22
* Lombok
* Map Struct
* Maven

## Download the project from GIT
https://github.com/christian-dev/companyUserManagement

Use https or ssh to clone locally 
There are 2 main branches : 
* develop
* master

For any imporvement, create branche and make a Pull request

## Build the project
After download the project using GIT commands, use Maven to build the project

### Build all
```bash
mvn clean install
````
### Build without tests
For a reason, you may want to do a quick action that not requires tests phases (for example adding a new jar in maven)
```bash
mvn clean install -DskipTests
```
You can also use the IDE integrated Maven features
## Deploy the application
After a successful build, you can run the applicaton using one the following options
### Run with maven
``` bash
mvn spring-booot:run
```
### Run java command line
``` bash
java -jar target/user-management-0.0.1-SNAPSHOT.war
```
### Run with Eclipse 
(Development mode)
Use the run application feature in Eclipse

### Run with IntellIJ
(Development mode)
Use the run application feature in IntellIJ

##  Request the application
Once the application is started, the API can be requested: 
* Register an user (POST method)
* Get details on an user (GET method)
* Get all users (GET method)
* Get an user by email (GET method)
* Update an user (PUT method)
* Partial update an user (PATCH method)
* Delete an user (DELETE method)

## Use Postman
The Postman collection is provided in the project
`CompanyUser.postman_collection.json`
## Curl
### Register an user
```bash
curl -d '{
    "firstName": "john",
    "lastName":"Doe",
    "birthdate": "18/08/1985",
    "phoneNumber": "+33667771245",
    "email": "john@doe.fr",
    "countryOfResidence": "FRANCE",
    "gender": "M"
}' -H "Content-Type: application/json" -X POST 
http://localhost:8080/api/v1/users/register

```

### GET an user details 
```bash
curl http://localhost:8080/api/v1/users/1
```
### GET all users
```bash
curl http://localhost:8080/api/v1/users/getAll
```
### Update an user
```bash
http://localhost:8080/api/v1/users/update/1
curl -d '{
    "firstName": "johnny",
    "lastName":"Doe",
    "birthdate": "18/08/1985",
    "phoneNumber": "+33667771245",
    "email": "johnny@doe.fr",
    "countryOfResidence": "FRANCE",
    "gender": "M"
}'-H "Content-Type: application/json" -X PUT 
```
### Partial update an user
```bash
curl -d '{
    "firstName":"Luc"
}' -X PATCH http://localhost:8080/api/v1/users/patch/1
```
### Delete an user 

```bash
curl -X DELETE
http://localhost:8080/api/v1/users/delete/1
```

## Technical implementation details
### Map struct
The application use Map Struct to create the mapping entity/Dto.

Only the interface (or abstract class) is required. The framework generates the implementation class. 

By default the implementation class is generated in `/target/generated-sources/annotations. It is customizable in the maven pom.xml.

Be aware to include the generated packages and classes in the classpath during the Run/Debugging in the development phase.
### Lombok
Used to generated class by simply annotate it. e.g Getter / Setter method, constructors equals, ...

### application.properties
The properties of the application. It must be in the classpath and it can be externalizable.
* Define the date format for the birthdate : `dd/MM/yyyy`. 
* Define the log file

You can also use the yml format (`application.yml`) of `applicatioon.properties`

### Validation of input
To validate the input for the registration of an user
#### Use javax.validation.constraints
Some default validation constraints definied in Java
* @Email
* @NotBlank
* @NotEmpty
* @Size

#### Custom constraints validation
##### @BirthDateConstraint
Only adults are allowed to create an account
##### @CountryOfResidenceConstraint
Only French residents are allowed to create an account
##### @GenderConstraint
The Gender value must be 'M' or 'F' 

### AOP to log input/output and Processing time of each call
Use the `@Aspect` annotation to defined the component responsible for the log.

Custom annotation `@LogExecutionContext` to annotated every method that need to be logged.

### Customize error message
Use of the @ControllerAdvice to define a class to handle the exception and rethrow the appropriate status codes.
The custom class extends the class `ResponseEntityExceptionHandler`  

## Actuator
There are some examples of endpoints that help to check and monitor the application 

* localhost:8080/api/v1/actuator/health
* localhost:8080/api/v1/actuator/env
* localhost:8080/api/v1/actuator/info
* localhost:8080/api/v1/actuator/beans

## Tests 
Use Junit 5

## Security
Currently, the security feature is not added to the application.
For add this feature, add the security starter.
`spring-boot-starter-security`
It will be available soon in the application



## Useful links
* [Spring Boot](https://spring.io/projects/spring-boot)
* [MapStruct](https://mapstruct.org/)
* [Lombok](https://projectlombok.org/)
* [Setup Lombok/IntellIJ](https://projectlombok.org/setup/)
* [Set Lombok/Eclipse](https://projectlombok.org/setup/)
* [Postman](https://www.postman.com/)
