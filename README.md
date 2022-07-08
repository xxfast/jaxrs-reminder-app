# jaxrs-reminder-app

## Architecture

![alt text](https://github.com/slarre/jaxrs-reminder-app/blob/d89bdc3f19cf19302b53441106c9fbc9c9f566e0/solution_architecture.png)

## Build

Requires Docker installed

`$ docker-compose build`

## Testing

Pending: Some blackbox testing like Cucumber with Selenium

## Run

It requires port 80 & 443 available on host

`$ docker-compose up`

## Usage

* Browse to https://localhost
* Login with any username & password

## API Documentation

There is a Swagger UI available in https://localhost/doc/

## Next steps

### Scalability

* User and reminders containers have the chance to increase workers and threads per worker configuration
* Nginx frontend performance could be boosted with more workers and a thread pool
* Get requests could be cached with Redis or MongoDB
* Change communication between microservices to gRPC

### New features

* Add user registration and change password UI and API
* Add reminders due date, alarms, notifications
* Add boards

### Other enhancements

* Add CSRF protection (it requires to resolve Swagger UI usage)
* Add owner to reminders in reminders service to loose coupling between user and reminders service

### Technical Debt/concerns

* Improve (a lot!) UI error management: use toast to show errors
* Try to parse swagger-config.yaml from api_gateway source code to ease maintenance
* Many opportunities to write common code between Python microservices
* Remove duplicated code between general docker-compose and services docker-compose files
* Remove duplicated code between user and reminders service dockerfiles
* Add OpenAPI specification to all internal services
* More consistent API in/out field names between services
* Use PEP8 (or similar) style guide to Python code.
* Add type hints to Python code
