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

1. Create a new Voting
```
POST /voting
{
   "name": "Voting Name" 
}
``` 

2. Create a new Associate
```
POST /associate
{
   "name": "Associate Name" 
}
```

3. Add an Associate to a Voting
```
POST /addassociatetovoting
{
   "votingId":1, "associateId": 2
}
```

4.  Open a Voting Session
```
POST /votingsession
{
   "votingId": 1, "duration": 600 
}
```

5. Receive an Associate Vote for an active Voting session
```
POST /vote
{
   "votingId": 1, "associateId": 2, "vote": true
}
```

6.  Get the Voting results
```
GET /voting/<id>
```

Extras:

*  Get all active Voting sessions
 ```
GET /voting?active=true
```

*  Get all Associates
 ```
GET /associate
```

## Decisions

*  REST endpoint is located at http://localhost:8080
*  Mapping: Many-To-Many because a Voting can have multiple Associates and an Associate can have multiple Voting.
*  The Associate must be assigned to the Voting before he can vote, otherwise he is not an participant of the voting.
*  AssociateVote: if the vote is null, the Associate has not voted yet. If calling /vote with null, InvalidVoteException is thrown.
*  Voting session duration is specified in seconds.
*  Voting session end time is store in the endTime attribute.
*  Total number of votes of a session is stored in the numberOfVotes attribute.
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

