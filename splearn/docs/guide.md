# 개발가이드

## 아키텍처
- 헥사고날 아키텍처
- 도메인 모델 패턴

### 계층
- Domain Layer
- Application Layer
- Adapter Layer

> 외부(Actor) -> 어댑터 -> 애플리케이션 -> 도메인

## 패키지
- domain
- application
    - required
    - provided
- adapter
    - webapi
    - persistence
    - integration
    - security 
