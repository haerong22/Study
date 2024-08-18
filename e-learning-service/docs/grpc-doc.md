### gRPC Protobuf 문서화 도구

#### protoc-gen-doc 플러그인
https://github.com/pseudomuto/protoc-gen-doc

- 설치
```shell
docker pull pseudomuto/protoc-gen-doc
```
- 실행
```shell
# html
docker run --rm \
  -v ./els-grpc-common/doc:/out \
  -v ./els-grpc-common/src/main/proto:/protos \
  pseudomuto/protoc-gen-doc
  
# markdown
docker run --rm \
  -v ./els-grpc-common/doc:/out \
  -v ./els-grpc-common/src/main/proto:/protos \
  pseudomuto/protoc-gen-doc --doc_opt=markdown,docs.md
```