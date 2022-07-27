# Datahub


Datahub is a search engine and cashing application that works in memory and responds to requests with a very short delay time.

[-> UI github repository designed with React](https://github.com/Caglayn/datahub-react-app)

### Libraries used
* JDK 18
* Spring Boot 2.6.7
* Spring Boot Web
* Spring Security
* Auth0 - JWT
* Lombok
* OpenApi UI
* MongoDb
* PostgreSQL
* MySql
* Spring Kafka for Apache Kafka
* Spring JDBC

### For Product Environment and CI/CD Process
* Deabian 11
* Docker
* DockerHub
* Github
* Kubernetes
* Jenkins
* Apache Kafka Broker and Zookeeper

### Microservices
* user-service (Authorization, authentication and user session management)
* data-service (core-service, manages data processing and storage)
* data-transform-service (accessing, processing and transferring data from different sources to data-service)
* And react-app (UI, web application for administration)

***

## Data-service commands & query syntax

### Commands
* Restore : Restores all data under given collectionName or all collections from backupDB
```
#restore:collectionName
#restore:all
```
<br />

* Backup : Backup all data under given collectionName or all collections to backupDB
```
#backup:collectionName
#backup:all
```

<br />

* Truncate : Truncate all data and indexed data under given collectionName or all collections in memory 
```
#truncate:collectionName
#truncate:all
```

<br />

* Index : Add, remove or update index for a collection
```
#index:collectionName #add:cloumnName
#index:collectionName #remove:cloumnName
#index:collectionName #update:cloumnName
```
<br />

### Queries
* Select query
```
select column1, column2, column3.. --(or "*")
from collectionName
where column1 = "condition" and/or column2 > condition and/or column3 < condition ...
```
<br />

### New commands and queries will be added as development continues..

[-> My LinkedIn](https://www.linkedin.com/in/caglayn/)

[-> caglayankaya@hotmail.com.tr](mailto:caglayankaya@hotmail.com.tr)