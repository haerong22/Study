# 분산 트랜잭션 - 주문 시스템

## Database

```shell
docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=1234 --name mysql mysql

docker exec -it mysql bash

mysql -u root -p

create database commerce;
```

## 요구사항
- 주문 데이터를 저장해야 한다.
- 재고관리를 해야한다.
- 포인트를 사용해야 한다.
- 주문, 재고, 포인트 데이터의 정합성이 맞아야 한다.
- 동일한 주문은 한 번만 이루어져야 한다.