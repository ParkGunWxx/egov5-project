package com.example.egov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * 전자정부프레임워크 5.0 + Spring Boot 3.3 + JPA + Oracle 메인 애플리케이션
 *
 * eGovFrame 5.0 Boot Starter 구성:
 *   - egovframe-boot-starter-access   : 접근제어 (메뉴/권한)
 *   - egovframe-boot-starter-crypto   : 암호화 (ARIA, SHA-256)
 *   - egovframe-boot-starter-security : Spring Security 연동
 */

/* GIT 형상 관리*/
@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.example.egov.repository")
@EntityScan(basePackages = "com.example.egov.entity")
@ConfigurationPropertiesScan
public class EgovApplication {

    public static void main(String[] args) {
        SpringApplication.run(EgovApplication.class, args);
    }
}
