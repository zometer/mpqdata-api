# mpqdata-api

## Overview

A spring-boot web API for querying Marvel Puzzle Quest data. 

## Usage

### gradle

````bash
# Build the project
$ ./gradlew build
````

### Java

````bash
# Run the application.
# Be sure to set the profile on the command line, otherwise you won't have a data source.
$ java \
    -Dmpq.api.deviceId=$DEVICE_ID \
    -Dspring.datasource.url=$DB_URL \
    -Dspring.datasource.username=$DB_USERNAME \
    -Dspring.datasource.password=$DB_PASSWORD \
    -jar \
    mpqdata-api-0.0.1-SNAPSHOT.jar
````

### Docker

Images are hosted at https://hub.docker.com/r/zometer/mpqdata-loader. 

````bash
# Run the application.
$ docker run -it \
    -e MPQ_API_DEVICEID=$DEVICE_ID \
    -e SPRING_DATASOURCE_URL=$DB_URL \
    -e SPRING_DATAUSER_USERNAME=$DB_USERNAME \
    -e SPRING_DATAUSER_PASSWORD=$DB_PASSWORD \
    zometer/mpqdata-api:latest
````

### Helm / Kubernetes

Helm charts are hosted at https://zometer.github.io/helm-charts. 

```bash
# Add the helm repository
$ helm repo add zometer https://zometer.github.io/helm-charts

# Install the chart, which creates the deployment, service, and ingress. 
$ helm install mpqdata-api zometer/mpqdata-api \
    -n mpqdata \
    --set config.db.url=$DB_URL,config.db.username=$DB_USERNAME,config.db.password=$DB_PASSWORD,config.cloudConfig.uri=$CLOUD_CONFIG_URL,config.mpq.api.deviceId=$DEVICE_ID

```

#### Example values.yaml

```yaml
config: 
  cloudConfig: 
    uri: http://mpqdata-config:8888
  db:
    url: jdbc:postgresql://localhost:5432/mpqdata
    username: db_user
    password: Super!Secure-Password-1231
  mpq: 
    api: 
      deviceId: DEVICE_ID
```

### Spring Profiles

| Name              | Notes |
|-------------------|-------|
| `default`         | This profile is loaded when none are specified and merged into all other profiles. When called with no other profiles, the version information output, with no other processing executed. |


### API 

#### REST 

API Supported Versions: 
  - 1: The initial API version. 

Endpoint: /api/rest/v{version}/hello

```bash 
# Hello Request 
$ curl 
```

```json 
```

#### GraphQL 


## Dependencies

### Java

1. JDK 17
1. Spring boot
1. Spring Cloud Config
1. Postgres Jdbc
1. Junit 5

### Databases

1. MPQDATA - Postgres database containing baseline MPQ character data.

### Environment Variables

| Name                     | Value                                                | Notes / Example      |
|--------------------------|------------------------------------------------------|----------------------|
| SPRING_PROFILES_ACTIVE   | Spring profiles to activate for a particular job run |                      |
| SPRING_DATASOURCE_URL    | URL for the mpqdata database. | `jdbc:postgresql://localhost:5432/mpqdata`      |
| SPRING_DATAUSER_USERNAME | Database username             | |
| SPRING_DATAUSER_PASSWORD | Database password             | |
| MPQ_API_DEVICEID         | Device Id needed to access MPQ API                   | |

## Issues

## TODOs


## Additional Information

1. https://proxyman.io

