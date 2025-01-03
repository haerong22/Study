### Demo Api Gateway

#### Environment
- Java 17
- SpringBoot
- Gradle
- Redis
- Prometheus
- Grafana
- Zipkin
- Docker, Docker Compose

#### Implementation
- API Gateway(spring cloud gateway)
  - Rate Limiter(redis)
  - BFF(mobile, web)
  - Filter(logging, cors, compression)
- Service Discovery(spring cloud eureka)
- Authorization & Authentication(spring security)
  - JWT
- Open Tracing(zipkin)
- Monitoring(prometheus, grafana)

```mermaid
graph LR;
    subgraph "Service Discovery"
        sd((Discovery Service))
    end
    subgraph "Mobile API Gateway"
        gw((Router))
        rl((Rate Limiter))
        sec((Spring Security))
        ze-mg((Zipkin Exporter))
        pe-mg((Prometheus Exporter))
    end
    subgraph "Web API Gateway"
        gw-web((Router))
        rl-web((Rate Limiter))
        sec-web((Spring Security))
        ze-wg((Zipkin Exporter))
        pe-wg((Prometheus Exporter))
    end
    subgraph "book-api"
        book["Book API"]
        ze-b((Zipkin Exporter))
    end
    subgraph "payment-api"
        payment["Payment API"]
        ze-p((Zipkin Exporter))
    end
    subgraph "Zipkin"
        zs["zipkin-server"]
        zu(("zipkin-ui"))
    end
    subgraph "Prometheus"
        ps["prometheus-server"]
    end
    subgraph "Grafana"
        gs["grafana-server"]
    end

    sd --> book
    sd --> payment
    gw --> sec
    gw --> rl
    gw --> sd
    gw-web --> sec-web
    gw-web --> rl-web
    gw-web --> sd
    rl --> sec
    rl-web --> sec-web
    sec --> book
    sec-web --> book
    sec --> payment
    sec-web --> payment

    zu --> zs
    ze-mg --> zs
    ze-wg --> zs
    ze-b --> zs
    ze-p --> zs

    pe-wg --> ps
    pe-mg --> ps

    gs --> ps
```