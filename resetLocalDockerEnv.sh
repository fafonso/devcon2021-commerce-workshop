#!/bin/sh

# Please adjust this variables to your project
dbvolume="devcon-commerce-2021-liferay_volume-mysql"
projectimage="devcon-commerce-2021"

echo "######################################################################################################################"
echo "################### If this project is deploying commerce, you will need to restart Liferay again! ###################"
echo "################### 1) Wait for your server to finish the initial startup                          ###################"
echo "################### 2) Stop your docker containes (cntrl + c)                                      ###################"
echo "################### 3) Execute docker-compose up                                                   ###################"
echo "######################################################################################################################"

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


