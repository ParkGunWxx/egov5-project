# ============================================================
# Stage 1: Build
# ============================================================
FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /app

# Gradle Wrapper 및 빌드 설정 파일 복사
COPY gradlew .
COPY gradle/ gradle/
COPY build.gradle .
COPY settings.gradle .

# Gradle 의존성 캐싱 (소스 변경 시 재다운로드 방지)
RUN chmod +x gradlew && ./gradlew dependencies --no-daemon || true

# 로컬 JAR 및 소스 복사
COPY libs/ libs/
COPY src/ src/

# 테스트 제외 후 빌드
RUN ./gradlew bootJar -x test --no-daemon

# ============================================================
# Stage 2: Runtime
# ============================================================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# 보안: non-root 사용자 생성
RUN addgroup -S egov && adduser -S egov -G egov

# 빌드된 JAR 복사
COPY --from=builder /app/build/libs/egov5-project.jar app.jar

# 파일 소유권 변경
RUN chown egov:egov app.jar

USER egov

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "app.jar"]
