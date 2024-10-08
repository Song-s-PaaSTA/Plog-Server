# OpenJDK 17 베이스 이미지 사용
FROM openjdk:17-jdk-alpine

# 애플리케이션이 배포될 독립 디렉터리 생성
RUN mkdir -p /usr/app/trash-service

# 애플리케이션이 위치할 경로 환경 변수로 설정
ENV APP_HOME=/usr/app/trash-service

# 작업 디렉터리를 설정
WORKDIR $APP_HOME

# JAR 파일을 복사
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} trash-service.jar

# 컨테이너에서 노출할 포트
EXPOSE 8004

# Spring 프로파일을 동적으로 설정할 수 있도록 CMD 명령어 수정
CMD ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar", "trash-service.jar"]

ENV SPRING_PROFILES_ACTIVE=k8s