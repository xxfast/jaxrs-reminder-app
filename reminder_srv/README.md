# Reminder Service

## Requirements

 * Python 3.10
 * Docker

`$ pip install -r requirements.txt`

## Run on development environment

`$ flask run`

## Run unit tests

`$ pytest`

## Build API docker image

`$ docker build -f dockerfiles/Dockerfile.api -t reminder_srv .`

## Run API docker image

`$ docker run -p 127.0.0.1:8080:80 reminder_srv`

Try API on localhost:8080

## Build API + DB docker images

`$ docker-compose build`

## Run API + DB docker images

`$ docker-compose up`
