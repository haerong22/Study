#### Architecture

![architecture](./docs/images/architecture.png)

#### Grafana Dashboard

https://grafana.com/grafana/dashboards/

- [spring cloud gateway](https://github.com/spring-cloud/spring-cloud-gateway/blob/main/docs/src/main/asciidoc/gateway-grafana-dashboard.json)

#### Docker Build

```shell
docker build -t els-course -f els-course/Dockerfile .
docker build -t els-chat -f els-chat/Dockerfile .
docker build -t els-discovery -f els-discovery/Dockerfile .
docker build -t els-enrollment -f els-enrollment/Dockerfile .
docker build -t els-file-manage -f els-file-manage/Dockerfile .
docker build -t els-gateway -f els-gateway/Dockerfile .
docker build -t els-graphql -f els-graphql/Dockerfile .
docker build -t els-playback -f els-playback/Dockerfile .
docker build -t els-user -f els-user/Dockerfile .
```

#### kubernetes Dashboard

- [설정 및 실행](./docs/kubernetes-dashboard.md)

#### documentation

- [gRPC API 문서화](./docs/grpc-doc.md)
- [REST API 문서화](./docs/rest-doc.md)
- [GraphQL API 문서화](./docs/graphql-doc.md)