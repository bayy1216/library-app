# 빌드 스테이지
FROM openjdk:11 as builder
WORKDIR /app

# Gradle Wrapper 복사
COPY gradlew .
COPY gradle gradle
RUN chmod +x ./gradlew

# 의존성 파일 복사 및 다운로드
COPY build.gradle .
COPY settings.gradle .
# 프로젝트에 gradle.properties가 있다면 주석을 풀고 사용해주세요.
# COPY gradle.properties .
RUN ./gradlew --no-daemon dependencies

# 소스코드 복사 및 애플리케이션 빌드
COPY . .
RUN ./gradlew --no-daemon clean build

# 실행 스테이지
FROM openjdk:11
COPY --from=builder /app/build/libs/*.jar /app/app.jar  
ENTRYPOINT ["java"]  
CMD ["-jar","/app/app.jar", "--spring.profiles.active=prod"]
