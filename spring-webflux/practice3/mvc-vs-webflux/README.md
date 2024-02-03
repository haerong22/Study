## Spring MVC vs Spring Webflux 성능테스트

### Jmeter 설치
```shell
brew install jmeter
```

- Number of threads(users) : 200
- Ramp-up period (seconds) : 1

#### Non I/O
`/health`

- mvc
![mvc.png](images/mvc.png)
- webflux
![webflux.png](images/webflux.png)

#### Redis I/O
`/users/1/cache`

- mvc
![mvc-redis.png](images/mvc-redis.png)
- webflux
![webflux-redis.png](images/webflux-redis.png)

#### Mysql I/O
`/users/1`

- mvc
![mvc-db.png](images/mvc-db.png)
- webflux
![webflux-db.png](images/webflux-db.png)