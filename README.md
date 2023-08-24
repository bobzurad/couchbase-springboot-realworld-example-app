# ![RealWorld Example App](logo.png)

> ### Spring Data + Couchbase codebase containing real world examples (CRUD, auth, advanced patterns, etc) that adheres to the [RealWorld](https://github.com/gothinkster/realworld) spec and API.

### [Demo](https://demo.realworld.io/)&nbsp;&nbsp;&nbsp;&nbsp;[RealWorld](https://github.com/gothinkster/realworld)


This codebase was created to demonstrate a realworld backend application built with **Spring Data Couchbase** including CRUD operations, authentication, routing, pagination, and more. For more information on how to this works with other frontends/backends, head over to the [RealWorld](https://github.com/gothinkster/realworld) repo.

# Getting started

This Project requires Java 17, Maven, and a running Couchbase Server instance. See the Setup Couchbase section for more details on how to setup a Couchbase instance.

## Configuration

This project needs a configured Couchbase configuration to run properly. The configuration is currently defined in `application.properties`

### Test Configuration

To run the postman test, you need to setup the following environment variables:

```
APIURL=http://localhost:8080/api
USERNAME=username
EMAIL=useremail@domain.com
PASSWORD=userPass
```

## Setup Couchbase

There are multiple ways to create a Couchbase instance. Below describes a couple of ways to run Couchbase.

### Using Docker

If you have Docker installed, you can run `docker-compose -f ./docker/docker-compose.yml up` to start the Couchbase instance locally. Then you can go to http://localhost:8091 to manage the Couchbase instance. 

#### First Time Setup

 If this is the first time starting Couchbase, you need to do the following:
 
 - Create the Administrator account with the password of `password`
 - Create a bucket named `default`

### Using Couchbase Capella

If you don't want to use Docker to run a Couchbase instance locally, you can go to https://cloud.couchbase.com. From there you can setup a trial account and get a 30 days free instance.

## Run

 - Make sure the Couchbase instance is running. 
 - Run `mvn package` in the project folder to build the project 
 - Run `java --add-opens java.base/java.lang=ALL-UNNAMED -jar target/*.jar`
 - Run `./postman/run-api-tests.sh` to run Postman collection tests

The reason we need to add the `--add-opens java.base/java.lang=ALL-UNNAMED` parameters can be found on this article: https://www.springcloud.io/post/2022-07/inaccessibleobjectexception/
