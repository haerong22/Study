## Webflux 학습
- java 17
- springboot 3.2.2

---

### Controller
- functional endpoint
- annotation endpoint

### Service

### Repository

---

### WebClient
- functional
- reactive nonblocking

---

### R2DBC
- MYSQL
```shell
docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=1234 --name r2dbc-mysql mysql:8 --character-set-server=utf8mb4 --collati
on-server=utf8mb4_unicode_ci
```

- Table
```mysql
create table users
(
    id         bigint auto_increment primary key,
    name       varchar(128),
    email      varchar(255),
    created_at datetime default CURRENT_TIMESTAMP not null,
    updated_at datetime default CURRENT_TIMESTAMP not null
);

create table posts
(
    id         bigint auto_increment primary key,
    user_id    bigint,
    title      varchar(30),
    content    varchar(200),
    created_at datetime default CURRENT_TIMESTAMP not null,
    updated_at datetime default CURRENT_TIMESTAMP not null
);

create index idx_user_id on posts(user_id);
```
- Repository
  - ReactiveCrudRepository
  - @Query
  - Custom Repository

---

### Reactive Redis
```shell
docker run -d -p 6379:6379 --name redis redis:6.2
```

- Reactive Stream
- Nonblocking I/O
- Spring Data Reactive Redis
  - lettuce