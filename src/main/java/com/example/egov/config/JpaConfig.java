package com.example.egov.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Optional;

/**
 * JPA 설정
 * - Auditing: 등록자/수정자 자동 주입 (Spring Security 연동)
 */
@Configuration
@EnableTransactionManagement
public class JpaConfig {

    /**
     * 현재 로그인 사용자를 Auditing 에 주입
     * eGovFrame 5.0 Security Starter 의 EgovUserDetails 와 연동
     */
    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !auth.isAuthenticated()
                    || "anonymousUser".equals(auth.getPrincipal())) {
                return Optional.of("SYSTEM");
            }
            return Optional.of(auth.getName());
        };
    }
}
