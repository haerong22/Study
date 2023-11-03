### database
```shell
docker pull mysql
docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=1234 --name mysql mysql
docker ps
docker exec -it mysql bash
```

```shell
mysql -u root -p
create database coupon_example;
use coupon_example;
```

