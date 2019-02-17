# Voting System

REST application to manage voting.

Features:
*  Create a new Voting
*  Create an Associate
*  Add an Associate to a Voting
*  Open a time-limited Voting Session 
*  Receive an Associate Vote for an active Voting ession
*  Get the Voting results
*  Get active 
 
## Getting Started

git clone this repository.

To run the embedded HSQLDB needed by the Voting System application, go into the hsqldb folder and run:
```
java -classpath lib/hsqldb.jar org.hsqldb.server.Server
```

To run the Voting System application, go into the project root folder and run:
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

*  Framework: Spring Boot, due to auto-configuration and included libraries (Jackson, JPA, Hibernate, Logger)
*  Mapping: Many-To-Many because a Voting can have multiple Associates and an Associate can have multiple Voting.

## Built With

 [Maven](https//maven.apache.org) - Dependency Management

## Authors

*  Lucas Gomes - [GitHub](https://github.com/lsgomes)

