#!/bin/sh

echo "Creating test database $3"

docker cp src/test/resources/data/schema-spring-batch.sql mysql-ex:/tmp/schema-spring-batch.sql
docker cp src/test/resources/data/schema-app.sql mysql-ex:/tmp/schema-app.sql
docker cp src/test/resources/data/monthly-trx-report-job-data.sql mysql-ex:/tmp/monthly-trx-report-job-data.sql

docker exec mysql-ex sh -c "MYSQL_PWD=$2 mysql -u$1 -e 'CREATE DATABASE $3'"
docker exec mysql-ex sh -c "MYSQL_PWD=$2 mysql -u$1 $3 < /tmp/schema-spring-batch.sql"
docker exec mysql-ex sh -c "MYSQL_PWD=$2 mysql -u$1 $3 < /tmp/schema-app.sql"
docker exec mysql-ex sh -c "MYSQL_PWD=$2 mysql -u$1 $3 < /tmp/monthly-trx-report-job-data.sql"

printf "Created test databse $3\n"