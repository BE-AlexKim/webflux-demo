# WebFlux Demo

Spring WebFlux와 Kotlin을 사용한 헥사고날 아키텍처 데모 프로젝트입니다.

## 기술 스택

- Kotlin 1.9.22
- Spring Boot 3.2.3
- Spring WebFlux
- R2DBC (MySQL)
- JUnit 5
- Mockito

## 아키텍처

이 프로젝트는 헥사고날 아키텍처를 따르며 다음과 같은 계층으로 구성되어 있습니다:

1. Domain Layer
   - 순수한 도메인 모델

2. Application Layer
   - Ports (인터페이스)
   - Services (유스케이스 구현)

3. Adapter Layer
   - Inbound Adapters (Web)
   - Outbound Adapters (Persistence)

## 기능

- 회원 가입 (이메일 중복 체크)
- WebFlux를 활용한 비동기 처리

## 테스트

각 계층별로 단위 테스트가 구현되어 있습니다:

- Domain Tests
- Application Layer Tests
- Web Adapter Tests
- Persistence Adapter Tests

## 실행 방법

1. MySQL 데이터베이스 준비:
   ```sql
   CREATE DATABASE webflux_demo;
   ```

2. application.yml 설정:
   ```yaml
   spring:
     r2dbc:
       url: r2dbc:mysql://localhost:3306/webflux_demo
       username: root
       password: your_password
   ```

3. 애플리케이션 실행:
   ```bash
   ./gradlew bootRun
   ```

## API 엔드포인트

### 회원가입
```http
POST /api/members/signup
Content-Type: application/json

{
    "email": "test@example.com",
    "password": "password123",
    "username": "testuser"
}
``` 