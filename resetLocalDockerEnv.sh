#!/bin/sh

# Please adjust this variables to your project
dbvolume="devcon-commerce-2021_volume-mysql"
projectimage="devcon-commerce-2021"

echo "1/6 --> Stoping your docker-compose environment"

#Stop docker containers
docker-compose stop

echo "2/6 --> Removing your docker containers"

#Remove docker containers
docker-compose rm

echo "3/6 --> Removing your mysql docker volume ($dbvolume)"

#Remove docker volume for mysql container
docker volume rm $dbvolume

echo "4/6 --> Removing your project docker image ($projectimage)"

#Remove docker image for our project
docker rmi $(docker images $projectimage -q)

echo "5/6 --> Building again your project's docker image"

#Rebuild docker image for our project
blade gw clean buildDockerImage

echo "6/6 --> Starting your docker environment"

#Start our docker compose again
docker-compose up


