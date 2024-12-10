# 🌱 𝗣𝗹𝗼𝗴-𝗦𝗲𝗿𝘃𝗲𝗿 🌱

> [Plog 프로젝트 설명 바로 가기](https://github.com/Song-s-PaaSTA)

```plaintext
* 이 프로젝트는 MSA 아키텍처를 기반으로 여러 독립적인 마이크로서비스를 포함하고 있습니다. 
각 서비스는 독립적으로 배포 및 확장 가능하고 Kubernetes(K8s)를 이용해 오케스트레이션됩니다.
```

</br>

## 👥 𝗠𝗲𝗺𝗯𝗲𝗿𝘀 𝗮𝗻𝗱 𝗥𝗼𝗹𝗲𝘀

| 이름        | 역할               | 담당 파트           |
|-------------|--------------------|---------------------|
| [노경희](https://github.com/khee2) | Backend            |  신고글, 쓰레기 장소  |
| [하고은](https://github.com/hagoeun0119) | Backend            | 회원, 플로깅 루트  |

</br>

## 📂 𝗣𝗿𝗼𝗷𝗲𝗰𝘁 𝗙𝗼𝗹𝗱𝗲𝗿 𝗦𝘁𝗿𝘂𝗰𝘁𝘂𝗿𝗲

```plaintext
Plog-Server
├── apigateway-service   
├── member-service       
├── plogging-service     
├── report-service       
├── trash-service        
├── eureka-server        
├── common               
├── k8s                  
│   ├── applications     
│   ├── environments     
├── .github             
├── query.sql            
├── Dockerfile           
├── README.md            
└── settings.gradle      
```

### 𝗗𝗶𝗿𝗲𝗰𝘁𝗼𝗿𝘆 𝘀𝘁𝗿𝘂𝗰𝘁𝘂𝗿𝗲 𝗯𝘆 𝘀𝗲𝗿𝘃𝗶𝗰𝗲

#### 𝟭. **𝗮𝗽𝗶𝗴𝗮𝘁𝗲𝘄𝗮𝘆-𝘀𝗲𝗿𝘃𝗶𝗰𝗲**
```plaintext
apigateway-service
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.songspaassta.apigatewayservice
│   │   │       ├── auth                  
│   │   │       ├── config                
│   │   │       ├── filter                
│   │   │       └── ApigatewayServiceApplication 
│   │   └── resources
│   │       ├── application.yml           
│   │       └── application-k8s.yml       
├── Dockerfile                            
└── build.gradle                          
```

#### 𝟮. **𝗺𝗲𝗺𝗯𝗲𝗿-𝘀𝗲𝗿𝘃𝗶𝗰𝗲** (𝗦𝗶𝗺𝗶𝗹𝗮𝗿 𝘁𝗼 𝗿𝗲𝗽𝗼𝗿𝘁, 𝘁𝗿𝗮𝘀𝗵, 𝗽𝗹𝗼𝗴𝗴𝗶𝗻𝗴)
```plaintext
member-service
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.songspaassta.memberservice
│   │   │       ├── client               
│   │   │       ├── config                
│   │   │       ├── controller            
│   │   │       ├── domain                
│   │   │       ├── dto                   
│   │   │       └── service               
│   │   └── resources
│   │       ├── application.yml           
│   │       └── application-k8s.yml       
├── Dockerfile                           
└── build.gradle                          
```

#### 𝟯. **𝗸𝟴𝘀**
```plaintext
k8s
├── applications
│   ├── apigateway
│   │   ├── ingress           
│   │   ├── autoscaler.yaml               
│   │   ├── deployment.yaml              
│   │   └── service.yaml                 
│   ├── eureka-server                    
│   ├── member-service                  
│   ├── plogging-service                 
│   ├── report-service                  
│   └── trash-service                   
├── environments
│   ├── configmaps
│   │   └── configmap.yaml                
│   └── secrets
│       └── secret.yaml                   
```

</br>

## 🔧 𝗧𝗲𝗰𝗵 𝗦𝘁𝗮𝗰𝗸

| **분류**       | **기술**                          | **설명**                                                                                           |
|----------------|-----------------------------------|---------------------------------------------------------------------------------------------------|
| **Backend**   | Spring Boot                       | MSA에 적합한 경량화 웹 서비스 개발 프레임워크                                                    |
|               | Spring Data JPA                   | ORM(Object-Relational Mapping) 기반으로 객체 지향적으로 DB와 상호작용                             |
|               | Spring Security                   | 사용자 접근 제어 강화 및 인증 시스템 강화                                                        |
|               | Spring Cloud Gateway              | API Gateway로 클라이언트와 서버 간의 라우팅 및 인증 관리                                         |
|               | Spring WebFlux                    | 비동기 방식의 Spring Gateway                                                                       |
|               | Spring Actuator                   | 서버의 모니터링과 관리를 지원하며 Graceful Shutdown 지원                                           |
|               | Eureka Server & Eureka Client     | 서비스 디스커버리 및 로드 밸런싱 제공                                                            |
|               | Java                              | 백엔드 개발 언어                                                                                  |
| **DB**        | PostgreSQL                        | 서비스 데이터 저장 DB                                                                             |
|               | Redis                             | 리프레시 토큰 관리                                                                                |
| **CI/CD**     | Naver Cloud Platform              | - **SourceCommit**: 코드 버전 관리 <br> - **SourceBuild**: Docker 이미지 자동 빌드 <br> - **SourcePipeline**: 코드부터 배포까지 자동화 <br> - **SourceDeploy**: Kubernetes 및 클러스터 배포 지원 <br> - **Container Registry**: Docker 이미지 저장, 배포 |
|               | K-PaaS                            | NCloud Kubernetes Service: 컨테이너 관리 및 오토스케일링                                         |
| **Open API**  | SK Open API                       | 애플리케이션 컨테이너 API 제공                                                                    |
|               | Naver Open API                    | 신고 위치 및 거리 계산, 지정 선택을 위한 좌표 변환 기능                                           |
| **Dataset**   | 공공데이터포털                     | 공공 데이터를 활용한 프로젝트 구성                                                               |
| **협업도구**   | Swagger                           | API 문서 자동화 도구                                                                              |


</br>

##  📊 **𝗣𝗿𝗼𝗷𝗲𝗰𝘁 𝗗𝗶𝗮𝗴𝗿𝗮𝗺𝘀**
| **𝗦𝗲𝗿𝘃𝗶𝗰𝗲 𝗔𝗿𝗰𝗵𝗶𝘁𝗲𝗰𝘁𝘂𝗿𝗲**   | **𝗦𝗲𝗿𝘃𝗶𝗰𝗲 𝗗𝗶𝘀𝗰𝗼𝘃𝗲𝗿𝘆**                                                                                                                                        |
|-----------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------|
| ![시스템 아키텍처](https://github.com/user-attachments/assets/3321ad4b-0e9b-41ae-ada9-6741d2309ee3) | ![서비스 디스커버리](https://github.com/user-attachments/assets/1e957555-4a77-4743-b605-68ca3a32efa0)                                     |

| **𝗖𝗹𝗼𝘂𝗱-𝗡𝗮𝘁𝗶𝘃𝗲 𝗔𝗽𝗽𝗹𝗶𝗰𝗮𝘁𝗶𝗼𝗻(𝗖𝗡𝗔)**          | **𝗖𝗜/𝗖𝗗**                                                                                                                                                   |
|-----------------------|------------------------------|
| ![CNA 개발](https://github.com/user-attachments/assets/28787b9b-bbea-4d85-b58e-d7dcbc6edc5f) | ![CI/CD](https://github.com/user-attachments/assets/ec26b341-4f77-4667-a7e6-9f178a628bd6)     |


#### 1. 서비스 배포 및 API 통신 흐름 요약
- Admin이 Git에 코드를 푸시하면 CI/CD 파이프라인이 시작
- SourceCommit에서 코드 수신 후 SourceBuild에서 Docker 이미지로 빌드하여 Container Registry에 저장
- SourcePipeline이 SourceDeploy로 이미지를 전달해 Kubernetes에 배포
- 배포된 서비스는 NCloud Kubernetes Service에서 운영
- Public IP → Load Balancer → Ingress를 통해 API Gateway가 사용자 요청을 서비스에 라우팅
- SK Open API와 Naver OpenAPI를 통해 외부 데이터와 연동

</br>

#### 2. 시스템 구성 및 배포 전략
- 플랫폼: NCloud Kubernetes Service(K-PaaS) 기반에서 마이크로서비스가 독립적으로 배포 및 관리되며, 오토스케일링으로 유연한 확장성과 높은 가용성 제공
- CI/CD: Naver Cloud의 SourceCommit, SourceBuild, SourcePipeline, SourceDeploy로 구성된 자동화된 파이프라인을 통해 Docker 이미지 빌드 및 배포
- MSA 구성: Plogging, Report, Trash, Member 서비스와 이를 지원하는 API Gateway, Eureka Server로 구성된 독립적인 마이크로서비스 구조
- 데이터베이스: 각 서비스는 독립적인 PostgreSQL 데이터베이스를 사용해 데이터를 저장


</br>

### 𝗘𝗥𝗗
![플로깅 루트 추천 앱 서비스 (1)](https://github.com/user-attachments/assets/6d6d0d3e-dcde-47c6-9c70-689a9d92d282)

</br>

## 🏗️ 𝟭𝟮-𝗙𝗮𝗰𝘁𝗼𝗿 𝗔𝗽𝗽
𝟭𝟮-𝗙𝗮𝗰𝘁𝗼𝗿 𝗔𝗽𝗽은 클라우드 환경에서 잘 작동하는 애플리케이션을 만들기 위한 𝟭𝟮가지 원칙입니다. </br>
설정은 코드와 분리하고, 데이터를 외부에서 관리하며, 빠르게 배포하고 쉽게 확장할 수 있도록 설계하는 방법을 제시합니다. </br>
즉, 개발부터 운영까지 더 간단하고 안정적으로 만들기 위한 가이드입니다.

### 𝘁𝗵𝗲 𝗺𝗮𝗶𝗻 𝗽𝗿𝗶𝗻𝗰𝗶𝗽𝗹𝗲𝘀
```plaintext
1. Codebase (코드 베이스)
2. Dependencies (종속성)
3. Config (설정)
4. Backing Services (백엔드 서비스)
5. Build, Release, Run
6. Stateless Processes (stateless 프로세스)
7. Port Binding (포트 바인딩)
8. Concurrency (동시성)
9. Disposability (폐기 가능)
10. Dev-Prod Parity (개발/프로덕션 환경 일치)
11. Logs (로그)
12. Admin Processes (관리 프로세스)
```

### '𝗣𝗹𝗼𝗴'의 𝟭𝟮-𝗙𝗮𝗰𝘁𝗼𝗿 적용 사항 일부 예시
![Group 1707481581](https://github.com/user-attachments/assets/dc6c4bed-88bc-4f5f-af28-3c6134680767)


</br>


## ☁️ 𝗖𝗹𝗼𝘂𝗱-𝗡𝗮𝘁𝗶𝘃𝗲 𝗔𝗽𝗽𝗹𝗶𝗰𝗮𝘁𝗶𝗼𝗻(𝗖𝗡𝗔)
'Plog'는 Cloud-Native Application(CNA) 원칙을 기반으로 마이크로서비스, 컨테이너, DevOps, CI/CD를 활용해 독립적이고 확장 가능한 아키텍처를 제공합니다.</br>
클라우드 네이티브를 위한 4가지 구성요소와 특징은 다음과 같습니다.

![Group 1707481577 (1)](https://github.com/user-attachments/assets/2225607d-cc71-4b25-b45f-597e16be210e)


</br>

## ✨ 𝗖𝗼𝗻𝘃𝗲𝗻𝘁𝗶𝗼𝗻𝘀
### 𝗖𝗼𝗺𝗺𝗶𝘁 𝗖𝗼𝗻𝘃𝗲𝗻𝘁𝗶𝗼𝗻𝘀

| **커밋 유형**       | **설명**                                                                 |
|--------------------|-------------------------------------------------------------------------|
| `Feat`             | 새로운 기능 추가                                                        |
| `Fix`              | 버그 수정                                                               |
| `Docs`             | 문서 업데이트                                                           |
| `Style`            | 코드 포맷 변경 (기능 변경 없음)                                          |
| `Refactor`         | 코드 리팩토링                                                           |
| `Test`             | 테스트 코드 추가                                                       |
| `Chore`            | 기타 변경 사항 (빌드 설정, 패키지 수정 등)                               |
| `Design`           | UI/UX 관련 디자인 수정                                                  |
| `Comment`          | 주석 추가 및 수정                                                       |
| `Rename`           | 파일 또는 폴더 이름 변경                                                |
| `Remove`           | 파일 삭제                                                               |
| `!BREAKING CHANGE` | 주요 변경 사항                                                          |
| `!HOTFIX`          | 긴급 수정                                                              |


#### 𝗥𝘂𝗹𝗲𝘀
1. 제목은 50자 이내로 작성하며 끝에는 마침표를 사용하지 않음
2. 제목과 본문은 한 줄 띄워 구분
3. 본문에는 변경 내용과 이유를 상세히 작성

#### 𝗘𝘅.
```bash
git commit -m "feat: 회원가입 기능 추가

- 회원가입 API 구현
- 유효성 검사 추가"
```

</br>

### 𝗕𝗿𝗮𝗻𝗰𝗵 𝗖𝗼𝗻𝘃𝗲𝗻𝘁𝗶𝗼𝗻𝘀 

| **브랜치 유형**      | **설명**                                                                 |
|--------------------|-------------------------------------------------------------------------|
| `develop`             | 배포한 최종 코드                                                |
| `feature/#<이슈번호>-<기능명>`  | 새로운 기능 개발 브랜치                                                  |
| `refactor/#<이슈번호>-<기능명>`  | 코드 리펙토링 브랜치                                                  |

</br>
