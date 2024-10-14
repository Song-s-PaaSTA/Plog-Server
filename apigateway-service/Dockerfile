# OpenJDK 17 베이스 이미지 사용
FROM openjdk:17-jdk-alpine

# 애플리케이션이 배포될 독립 디렉터리 생성
RUN mkdir -p /usr/app/apigateway-service

# 애플리케이션이 위치할 경로 환경 변수로 설정
ENV APP_HOME=/usr/app/apigateway-service

# 작업 디렉터리를 설정
WORKDIR $APP_HOME

# gradlew 파일과 필요한 스크립트, 소스 코드를 복사
COPY . .

# gradlew에 실행 권한 부여
RUN chmod +x ./gradlew

# gradlew clean build 실행
RUN ./gradlew clean build

# JAR 파일을 복사
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} apigateway.jar

# 컨테이너에서 노출할 포트
EXPOSE 8000

# Spring 프로파일을 동적으로 설정할 수 있도록 CMD 명령어 수정
CMD ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar", "apigateway.jar"]

ENV SPRING_PROFILES_ACTIVE=k8s