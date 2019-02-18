# Voting System

REST application to manage voting.

Features:
*  Create a new Voting
*  Create an Associate
*  Add an Associate to a Voting
*  Open a time-limited Voting Session 
*  Receive an Associate Vote for an active Voting session
*  Get the Voting results
*  Get all active Voting sessions
 
## Getting Started

Download the project source code through:

```
git clone https://github.com/lsgomes/votingsystem.git
```

Or download the standalone version in the release tab.

Execution order:

1. The database must be up.
2. Run the Voting System application.

Steps:

*   To run the embedded HSQLDB needed by the Voting System application, go into the hsqldb folder and run:
```
java -classpath lib/hsqldb.jar org.hsqldb.server.Server
```

*  To run the Voting System without download the source code, download the release version and run:

```
java -jar votingsystem.jar
```

*  Otherwise, to download all dependencies, compile and run the Voting System application, go into the project root folder and run:
```
mvnw spring-boot:run
```

## Usage

Postman collection for usage is included in the postman folder.

* Create a new Voting
```
POST /voting
{
   "name": "Voting Name" 
}
``` 

*  Create a new Associate
```
POST /associate
{
   "name": "Associate Name" 
}
```

*  Add an Associate to a Voting
```
POST /addassociatetovoting
{
   "votingId":1, "associateId": 2
}
```

*  Open a Voting Session
```
POST /votingsession
{
   "votingId": 1, "duration": 600 
}
```

*  Receive an Associate Vote for an active Voting session
```
POST /vote
{
   "votingId": 1, "associateId": 2, "vote": true
}
```

*  Get the Voting results
```
GET /voting/<id>
```

*  Get all active Voting sessions
 ```
GET /voting?active=true
```

## Decisions

*  Mapping: Many-To-Many because a Voting can have multiple Associates and an Associate can have multiple Voting.
*  The Associate must be assigned to the Voting before he can vote, otherwise he is not an participant of the voting.
*  AssociateVote: if the vote is null, the Associate has not voted yet.
*  POST synchronized methods for thread-safety and multiple requests support.
*  @ControllerAdvice annotation for HTTP error handling.
*  Any invalid action or state mapped to exceptions, HTTP Status 403 Forbidden.
*  Java code documentation.
*  No custom handling/advices for Jackson serialization/de-serialization and database exceptions, due to the limited time.
*  Due to the limited time to develop the project, no unit tests yet.


## Built with

*  Framework: [Spring Boot](https://spring.io/projects/spring-boot), due to auto-configuration and included libraries (Jackson, JPA, Hibernate, Logger, Tomcat)
*  Embedded Database: [HSQLDB](http://hsqldb.org) due to native Hibernate support.
*  [Lombok](https://projectlombok.org) to reduce boilerplate code.
*  [Maven](https://maven.apache.org) for Dependency Management.
*  [Data Transfer Object](https://martinfowler.com/eaaCatalog/dataTransferObject.html) for encapsulation.



## Authors

*  Lucas Gomes - [GitHub](https://github.com/lsgomes)

