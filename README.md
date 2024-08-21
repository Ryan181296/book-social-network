# book-social-network
This microservice is responsible for:
* Onboarding users
* Roles and permissions
* Authentication

## Tech stack
* Build tool: maven >= 3.9.8
* Java: 21
* Framework: Spring boot 3.2.x
* DBMS: MySQL

## Prerequisites
* Java SDK 21
* A MySQL server

## Start application
`mvn spring-boot:run`

## Build application
`mvn clean package`
## Streaming message event with `Kafka`
https://kafka.apache.org/documentation/#configuration
## Docker `cmd` guideline
### Build docker image
`docker buildx build --platform <platform_name> -t <docker_hub_account>/<image_name>:<image_version> .`
#### Example:
`docker buildx build --platform linux/amd64 -t loung0303/identity-service:0.0.1 .`
### Push docker image to Docker Hub
`docker image push <docker_hub_account>/<image_name>:<image_version>`
#### Example:
`docker image push loung0303/identity-service:0.0.1`
### Pull docker image from Docker Hub
`docker pull <docker_hub_account>/<image_name>:<image_version>`
#### Example:
`docker pull loung0303/identity-service:0.0.1`
### Create network:
`docker network create <network_name>`
#### Example:
`docker network create identity-network`
### Start MySQL in network
`docker run --network <network_name> --name <image_name> -p <connection_port> -e MYSQL_ROOT_PASSWORD=<password> -d mysql:<tag>`
#### Example:
`docker run --network identity-network --name mysql-oracle -p 3306:3306 -e MYSQL_ROOT_PASSWORD=Theluong1503 -d mysql:oracle`
### Start MongoDB in network
`docker run --network <network_name> --name <image_name> -p <connection_port> -e MONGODB_ROOT_USERNAME:<username> -e MONGODB_ROOT_PASSWORD=<password> mysql:<tag>`
#### Example:
`docker run --network identity-network --name mongo -p 27017:27017 -e MONGODB_ROOT_USERNAME:root -e MONGODB_ROOT_PASSWORD=Theluong1503  mongo:latest`
### Run your application in network
`docker run --name <image_name> --network <network_name> -p <connection_port> -e DBMS_CONNECTION=jdbc:mysql://<database_image_name>:<connection_port>/<schema_name> <docker_hub_account>/<image_name>:<image_version>`
#### Example:
`docker run --name identity-service --network identity-network -p 8080:8080 -e DBMS_CONNECTION=jdbc:mysql://mysql-oracle:3306/identity_service loung0303/identity-service:0.0.1`

## Deploy your application to `AWS`
### 1. Visit `Console Home`
https://ap-northeast-1.console.aws.amazon.com/console/home?nc2=h_ct&region=ap-northeast-1&src=header-signin#
### 2. Launch an instance
- Click `EC2` > `Instances` > `Launch an instance`
- Choose `Name and tags` > `Application and OS Images` > `Instance type` > `Key pair` (use to login to the server) > `Configure storage` (15GB if using for `free`) > `Launch instance`
- Open port for `DBMS` and your application
### 3. using `Termius` to deploy the application
- Download and install `Termius` app (https://termius.com/download/macos)
- Click `Keychain` > Import `Cetificate`
- Click `Hosts` > `Create host` > Enter `Server's IP address` to access

### 4. Install Docker using the `apt` repository
https://docs.docker.com/engine/install/ubuntu/#install-using-the-repository

### 5. Install and run database image from Docker Hub