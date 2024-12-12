### Test DB setting
- create user
```mysql
CREATE USER 'test'@'%' IDENTIFIED BY '1234';
GRANT CREATE, DROP ON *.* TO 'test'@'%';
GRANT ALL PRIVILEGES ON `test_db_%`.* TO 'test'@'%';
FLUSH PRIVILEGES ;
```
---
### Chaos Monkey
- https://codecentric.github.io/chaos-monkey-spring-boot/latest/

`build.gradle`
```groovy
implementation 'de.codecentric:chaos-monkey-spring-boot:3.1.0'
```

`application-chaos-monkey.yml`
```yaml
spring:
  profiles:
    active: chaos-monkey
chaos:
  monkey:
    enabled: true
    watcher:
      service: true
    assaults:
      latencyActive: true

management:
  endpoint:
    chaosmonkey:
      enabled: true
    chaosmonkeyjmx:
      enabled: true

  endpoints:
    web:
      exposure:
        include: "*"
```

`run`
```shell
java -jar your-app.jar --spring.profiles.active=chaos-monkey --chaos.monkey.enabled=true --chaos.monkey.watcher.service=true --chaos.monkey.assaults.latencyActive=true
```