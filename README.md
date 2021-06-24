# Pokedex

Simple REST API for Jit Team Internship Java exercise (and Golang). It was created with Spring Boot, Spring Data and in-memory database H2 to store information about Pokemons.  
The application uses also Swagger-UI, HATEOAS, Actuator.

## Prerequisites

Following platform is required to run the application:

- Java JDK 8.

## Endpoints

### REST API

#### Get all Pokemons
GET http://localhost:8080/api/pokemons

#### Get Pokemon by id
GET http://localhost:8080/api/pokemons/{id}

#### Get Pokemons by name (e.g. Pikachu)
GET http://localhost:8080/api/pokemons/?name=Pikachu

#### Add new pokemon (e.g. Pikachu)
POST http://localhost:8080/api/pokemons  
content type: application/json  
body:  
{  
"name" : "Pikachu"  
}

#### Update or add new pokemon (when there isn't pokemon with given id)
PUT http://localhost:8080/api/pokemons/{id}  
content type: application/json  
body:  
{  
"name" : "Pikachu"  
}

If there isn't a pokemon on given id, then add new pokemon with next available id.

#### Delete pokemon with given id
DELETE http://localhost:8080/api/pokemons/{id}

### Others

#### The application status
http://localhost:8080/actuator/health

#### Swagger-UI
http://localhost:8080/swagger-ui/

#### Swagger api
http://localhost:8080/v2/api-docs

#### H2 console
http://localhost:8080/h2-console/  

user:sa  
pass:<empty>

### Tests
The repository includes a postman collection in the file  
Pokemon API.postman_collection.json