#!/bin/bash

# stop when an error occurs
set -e

if [[ -z "$REMAINDER_APP_DATASOURCE_HOST" ]]; then
    echo "Using default database host 'mysql'"
    REMAINDER_APP_DATASOURCE_HOST=mysql
fi
if [[ -z "$REMAINDER_APP_DATASOURCE_NAME" ]]; then
    echo "Using default database name 'audit_'${REMAINDER_APP_ENVIRONMENT}"
    REMAINDER_APP_DATASOURCE_NAME=audit_${REMAINDER_APP_ENVIRONMENT}
fi
if [[ -z "$REMAINDER_APP_DATASOURCE_USERNAME" ]]; then
    echo "REMAINDER_APP_DATASOURCE_USERNAME not set!"
    exit 1
fi
if [[ -z "$REMAINDER_APP_DATASOURCE_PASSWORD" ]]; then
    echo "REMAINDER_APP_DATASOURCE_PASSWORD not set!"
    exit 1
fi
if [[ -z "$REMAINDER_APP_ROOT_USERNAME" ]]; then
    echo "REMAINDER_APP_ROOT_USERNAME not set!"
    exit 1
fi
if [[ -z "$REMAINDER_APP_ROOT_PASSWORD" ]]; then
    echo "REMAINDER_APP_ROOT_PASSWORD not set!"
    exit 1
fi

# Hostname of the database server

# Table name
TABLE_NAME="remainderapp"

# Apply the flyway migration
# This will initialize the database schema if it does not exist,
# And then apply all the necessary migrations.
bash /flyway/flyway -n -url=jdbc:mariadb://$REMAINDER_APP_DATASOURCE_HOST \
-user=$REMAINDER_APP_ROOT_USERNAME -password=$REMAINDER_APP_ROOT_PASSWORD \
-installedBy='Init container' \
-schemas=$REMAINDER_APP_DATASOURCE_NAME \
-placeholders.dbSchema=$REMAINDER_APP_DATASOURCE_NAME \
-locations=filesystem:/flyway/sql \
-baselineOnMigrate=true \
-cleanDisabled=true \
migrate

# Privileged mysql connection
MYSQL_ROOT="/usr/bin/mysql -u$REMAINDER_APP_ROOT_USERNAME -p$REMAINDER_APP_ROOT_PASSWORD -h$REMAINDER_APP_DATASOURCE_HOST"

# Create the user if it does not exists
echo "CREATE USER IF NOT EXISTS '$REMAINDER_APP_DATASOURCE_USERNAME'@'%';" | $MYSQL_ROOT

# Set the password to $REMAINDER_APP_DATASOURCE_PASSWORD. the mysql server uses secure-auth
echo "SET PASSWORD FOR '$REMAINDER_APP_DATASOURCE_USERNAME'@'%' = PASSWORD('$REMAINDER_APP_DATASOURCE_PASSWORD');" | $MYSQL_ROOT

# Set Usage permissions (taken from ../../README.md)
echo "GRANT USAGE ON *.* TO '$REMAINDER_APP_DATASOURCE_USERNAME'@'%';" | $MYSQL_ROOT

# Grant Select and Insert privileges on the $TABLE_NAME table
echo "GRANT SELECT,INSERT ON TABLE $REMAINDER_APP_DATASOURCE_NAME.$TABLE_NAME TO '$REMAINDER_APP_DATASOURCE_USERNAME'@'%';" | $MYSQL_ROOT

echo "MySQL DB Init Container has completed"
