# Payments Processing Application
This is RESTful web service dedicated for processing of payments

 
## Getting Started

### Prerequisites

* Java 8+
* Maven 3.6.x
* Tomcat 9.0.29
* H2 Database 1.4.200


### Installing

To install this application, run the following commands:

```
git clone https://github.com/giedriusgen/PaymentApp.git
```

This will get a copy of the project installed locally. To install all of its dependencies and start app, follow the instructions below.
To run Spring Boot API, cd into the payment-app folder and run:


```
mvn clean install spring-boot:run -Dspring-boot.run.arguments=--server.port=8081
```


### Using


* Browse to http://localhost:8081/swagger-ui.html to use Swagger
* Browse to http://localhost:8081/console to connect to database. In JDBC URL field enter db path: jdbc:h2:D:/paymentInfo/payment.db. Username: sa. Password: (do not write anything)
* Application logs together with database files are saved in D disc paymentInfo directory
* Location of those files can be changed in application.properties and logback-spring.xml files
