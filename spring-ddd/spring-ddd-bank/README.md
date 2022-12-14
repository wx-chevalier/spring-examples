Project Spring DDD Bank
=======================
A sample project following Domain Driven Design with Spring Data JPA

                                            (C) Christoph Knabe, 2017-03-17 ... 2018-10-11

In this project I am trying to apply principles of Domain Driven Design.
In contrary to full-blown DDD examples on the web I am applying here some simplifications.
This sample application is used for a course on Software Engineering at Beuth University of Applied Sciences Berlin.

This project uses

- JDK 8
- Maven 3
- Spring Boot
- Spring Data + JPA + Hibernate + Derby
- AspectJ Compile Time Weaving for main sources
- `springfox-swagger` for generating documentation and user interface for the REST service
- JUnit 4
- The Exception Handling and Reporting Framework MulTEx

Detailed version indications you can find in the file `pom.xml`.

By  `mvn clean test`   all necessary libraries will be fetched, the project will be compiled, exception message texts will be collected and the test suite will be executed.

After this is working you can import the Maven project into your Java IDE 
(Spring Tool Suite is recommended, as AspectJ weaving is needed for the compile phase).

You can run the application (a REST server) in your IDE by running class `de.beuth.knabe.spring_ddd_bank.Application` or on the command line after `mvn package` by `java -jar target/spring-ddd-bank-0.1-SNAPSHOT.jar`. In the last lines of the log you will see the number of the port (usually 8080), on which the server will listen. You can stop it by typing &lt;Ctrl/c&gt;.

## Which DDD principles are implemented?

- Modelling the domain layer as one package, which does not depend on any other package besides standard Java SE packages as `java.time` and `javax.persistence`. The latter only for the JPA annotations.

- Avoid an [anemic domain model](https://martinfowler.com/bliki/AnemicDomainModel.html) by having relevant business logic methods in entity class `Client`.  
  This requires the feature **Domain Object Dependency Injection** (DODI), which can only be implemented by using full AspectJ compile-time weaving. 
  See [??11.8.1 Using AspectJ to dependency inject domain objects with Spring](http://docs.spring.io/spring/docs/4.3.x/spring-framework-reference/html/aop.html#aop-atconfigurable).

- The Domain Layer references required services only by self-defined, minimal interfaces (in package `domain.imports`).

- Implementing required services in the infrastructure layer (in package `infrastructure`).

- Linking together required services and their implementations by Dependency Injection. 

- Implementing an interface layer for external access to the application. 
  This is implemented as a REST service in package `rest_interface`.


## Other Characteristics

- It is a little bank application, where the bank can create clients and clients can create and manage accounts, e.g. deposit and transfer money.
- The analysis class diagram is in file [src/doc/BankModel.pdf](src/doc/BankModel.pdf). Its editable source by UMLet has extension `.uxf`.
- An overview of the REST endpoints is given at [src/doc/REST-API.md](src/doc/REST-API.md). Additionally when running the REST service, the REST endpoint documentation generated by Swagger, is accessible at http://localhost:8080/ and clicking on [REST API documentation](http://localhost:8080/swagger-ui.html).   
- As simplification an application layer is not implemented, but the interface layer is made transactional.
- Internationalizable, parameterizable exception message texts
- Capture of each exception message text in the reference language directly as main JavaDoc comment of the exception
- The application runs against a file-based Derby database. This is configured in file [src/main/resources/application.properties](src/main/resources/application.properties)
- Tests are run against an empty in-memory Derby database. This is configured in file [src/test/resources/application.properties](src/test/resources/application.properties)
- Generation of a test coverage report by the [JaCoCo Maven plugin](http://www.eclemma.org/jacoco/trunk/doc/maven.html) into [target/site/jacoco-ut/index.html](file:target/site/jacoco-ut/index.html).
- Simple Spring Security with a fixed number of predefined users (1 bank, and 4 clients).

### Where are the exception message texts?
In the file `MessageText.properties`. The editable original with some fixed message texts is in `src/main/resources/`.
By Maven phase `compile` this file is copied to `target/classes/`.
During the following phase `process-classes` the exception message texts are extracted from the JavaDoc comments of all exceptions under `src/main/java/`
by the  `ExceptionMessagesDoclet`  as configured for the `maven-javadoc-plugin`. They are appended to the message text file in `target/classes/`.
This process is excluded from m2e lifecycle mapping in the `pom.xml`.


## Plans

- Make `Amount` a better value object by freezing its attributes. Seems, that for this goal Hibernate has to be used instead of JPA.
- Nice to have: Avoid own ID of `AccountAccess`, because this class models an m:n association between `Client` and `Account`. 
  There should not be a possibility for several links between a client and an account.
  This would require the usage of `client.id` and `account.id` as a composite ID for `AccountAccess`.
  Not so easy, see [JPA: How to associate entities with relationship attributes?](http://stackoverflow.com/questions/18739334/jpa-how-to-associate-entities-with-relationship-attributes)
- Implement another kind of persistence interface beside Spring Data JPA, 
  e.g. JPA Criteria API, as the interface-based implementation of Spring Data
  is confusing to beginners.
- Implementation of real unit tests with mock implementations of the repository interfaces.

## References and Sources
- [Detailed example text about DDD](https://www.mirkosertic.de/blog/2013/04/domain-driven-design-example/)
- [The DDD Sample project](https://github.com/citerus/dddsample-core), from which are taken some inspirations
- [The Ports and Adapters Pattern](http://alistair.cockburn.us/Hexagonal+architecture)
- [Can DDD be Adequately Implemented Without DI and AOP?](https://www.infoq.com/news/2008/02/ddd-di-aop)
- [Spring Boot](https://spring.io/guides/gs/spring-boot/)
- [Spring Dependency Injection](http://projects.spring.io/spring-framework/)
- [Spring Data JPA](https://spring.io/guides/gs/accessing-data-jpa/)
- [Spring Data JPA Query Methods](http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods)
- This application implements my knowledge about layering and data access at a given point of time. Previous versions were in a 3-layer form in german under [Bank3Tier - Bankanwendung in 3 Schichten](http://public.beuth-hochschule.de/~knabe/java/bank3tier/).
