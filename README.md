# GoalMate Server

![GoalMate Banner](https://github.com/user-attachments/assets/a2208f50-dd1f-4181-858c-741965f10736)

> **이루고 싶은 목표는 있지만 어디서부터 시작해야 할지 막막한 멘티에게,  
> 그 길을 먼저 걸어본 멘토가 함께하며 목표를 현실로 만듭니다.**

**GoalMate**는 목표 설정과 달성 과정을 지원하고, 동기 부여를 제공해 주는 서비스입니다.
본 저장소(`GoalMate-Server`)는 **GoalMate**의 백엔드 API 서버 코드를 관리하고 있으며, AWS 환경에서 배포하고 있습니다.

***[서비스 링크]***

- **iOS**: [App Store 링크](https://apps.apple.com/kr/app/%EA%B3%A8%EB%A9%94%EC%9D%B4%ED%8A%B8/id6741704150)
- **Android**: [Play Store 링크](https://play.google.com/store/apps/details?id=cmc.goalmate&hl=ko)

---

### 기술 스택

- **언어 / 프레임워크**
    - Java 17
    - Spring Boot 3.4.1
- **데이터베이스 / 캐시**
    - MySQL (AWS RDS)
    - Redis
- **인프라**
    - AWS EC2, RDS, S3
    - GitHub Actions (CI/CD)
    - Docker Compose / Docker Swarm

---

### 스펙 주도 개발(OpenAPI)

- **GoalMate**는 OpenAPI 스펙을 활용하여 **스펙 주도 개발**(Specification-Driven Development)을 진행하고 있습니다.
- API 문서 작성은 [OpenAPI 3.0](https://swagger.io/specification/) 스펙을 따릅니다.
- API
  문서는 [goalmate-spec](https://github.com/Central-MakeUs/GoalMate-Server/blob/main/src/main/resources/api/goalmate-spec.yaml)
  과 [Swagger UI](https://api.goalmate.site/swagger-ui/index.html) 등을 통해 확인할 수 있습니다.

---

### Docker Swarm

- **Docker Swarm**을 이용해 다중 컨테이너 환경을 구성했습니다.
- 서비스 확장 및 배포 시 Swarm 스택 배포를 이용합니다.
  ```bash
  cd deploy
  docker swarm init
  docker stack deploy -c docker-compose.yaml goalmate
  ```

- **Docker Swarm**을 통해 무중단 배포(Rolling Update)를 지원합니다.
- GitHub Actions를 통해 CI/CD 파이프라인을 자동화하여 배포 효율을 높였습니다.
- local 환경에서의 개발 및 테스트를 위해 `docker-compose`를 사용하여 DB와 Redis를 포함한 개발 환경을 구성했습니다.

---

### API 서버

- 프로덕션 서버 주소: [https://api.goalmate.site](https://api.goalmate.site)
- API 문서: [https://api.goalmate.site/swagger-ui/index.html](https://api.goalmate.site/swagger-ui/index.html)

---

### 관리자 페이지

- 관리자 페이지: [https://goalmate-web-beta.vercel.app/](https://goalmate-web-beta.vercel.app/)
- 주요 기능:
    - 멘토의 목표 등록, 수정, 삭제
    - 멘티 진행 상황 확인
    - 코멘트 관리
    - 기타 관리자 기능
