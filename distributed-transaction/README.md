## Database

```shell
docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=1234 --name mysql mysql

docker exec -it mysql bash

mysql -u root -p

create database commerce;
```