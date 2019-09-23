
## Overall Architecture

This project is a Spring Boot Java application to load & retrieve json data in Tree struture.
Neo4j is used as the database. Neo4j is one of the most popular graph database management system developed by Neo4j, Inc. 
It is an ACID-compliant transactional database with native graph storage and processing.


## Build & Deploy Process

Either download the application or use git to clone the application:

git clone git@github.com:boneyqb/springboot-neo4j.git

### Build Docker Image

Run the below command to build the docker image: 

docker build . -t springboot-neo4j

* If this succeeds, run the command:

docker-compose up

Once the application is up and running the API details are available at http://localhost:8085/swagger-ui.html

#### Loading the initial dataset

You may notice that there is no data for you to interact with. 

To fix this, hit the following endpoint from your browser or using curl:

http://localhost:8085/node/load-json

This will pre-load the Neo4j database with data stored in sample.json saved under /resources folder.

The database is available at http://localhost:7474/ 
Username: neo4j
Password: password
