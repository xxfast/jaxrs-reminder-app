FROM alpine:3.14

WORKDIR /flyway

ENV FLYWAY_VERSION 5.2.4

USER root

RUN apk --no-cache add --update bash openssl \
  && wget https://repo1.maven.org/maven2/org/flywaydb/flyway-commandline/${FLYWAY_VERSION}/flyway-commandline-${FLYWAY_VERSION}.tar.gz \
  && tar -xzf flyway-commandline-${FLYWAY_VERSION}.tar.gz \
  && mv flyway-${FLYWAY_VERSION}/* . \
  && rm flyway-commandline-${FLYWAY_VERSION}.tar.gz \
  && ln -s /flyway/flyway /usr/local/bin/flyway


WORKDIR /

# Update the mysql-client
RUN apk add --no-cache mysql-client 

# Copy the sql scripts
COPY sql /flyway/sql/

# Copy the entrypoint script
COPY docker-entrypoint.sh /


# Run the entrypoint script
ENTRYPOINT ["/docker-entrypoint.sh"]


