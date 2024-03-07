## 배달 플랫폼 프로젝트
- java 17
- kotlin 1.9.22
- springboot 3.2.2
- gradle
- mysql
- jpa
- swagger
- security
- rabbitmq
- spring cloud gateway
- spring boot admin

---

## 모듈

### API
- swagger : http://localhost:8080/swagger-ui/index.html
- Filter를 통한 Request, Response 로그
- 공통 Api 응답 및 에러코드 정의 
- Exception Handler
- Interceptor를 통한 인증 처리
- JWT 토큰 발행 및 사용자 인증
- Spring Security 활용 사용자 인증
- Rabbitmq 활용 비동기 메시지 처리
- gateway 서버 인증 및 라우팅
- ELK stack 을 통한 로그 모니터링
- Spring Boot Admin 을 통한 애플리케이션 모니터링

---

### Store admin
- Rabbitmq 활용 비동기 메시지 처리
- SSE 활용 알림 전송

---

### DB

---

### RabbitMQ

실행
```shell
docker-compose -f rabbitmq/docker-compose.yml up -d
```
Management 활성화
```shell
rabbitmq-plugins enable rabbitmq_management
```
관리자 페이지 접속
- http://localhost:15672

---

## FLOW
### 주문 비동기 메시징 처리
![rabbitmq.png](images/rabbitmq.png)


---

## 모니터링

### Log 모니터링
- logback
- ELK stack
  - Kibana : `http://localhost:5601`
  - id/pw : elastic/changeme

```shell
cd docker-compose/elk-stack
docker-compose up -d
```

### Application 모니터링
- spring boot admin
  - `http://localhost:8085`
- prometheus, grafana
  - grafana : `http://localhost:3000`
```shell
cd docker-compose/prometheus-grafana
docker-compose up -d
```