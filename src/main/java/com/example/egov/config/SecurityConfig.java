package com.example.egov.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * eGovFrame 5.0 Security 설정
 * egovframe-boot-starter-security 의 EgovSecurityConfiguration 을 기반으로 구성
 */
@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * Security Filter Chain 구성
     * eGovFrame 5.0 권장 패턴
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // CSRF: REST API 이므로 비활성화 (화면 포함 시 활성화 권장)
            .csrf(AbstractHttpConfigurer::disable)

            // X-Frame-Options: SAMEORIGIN (전자정부 표준 - iframe 허용)
            .headers(headers -> headers
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
            )

            // 요청 권한 설정
            .authorizeHttpRequests(auth -> auth
                // 공개 경로 (인증 불필요)
                .requestMatchers(
                    "/",
                    "/login",
                    "/api/public/**",
                    "/h2-console/**",
                    "/css/**", "/js/**", "/images/**", "/favicon.ico"
                ).permitAll()
                // 관리자 전용 경로
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                // 나머지는 인증 필요
                .anyRequest().authenticated()
            )

            // 로그인 설정
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/loginProcess")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error")
                .permitAll()
            )

            // 로그아웃 설정
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            );

        return http.build();
    }

    /**
     * 비밀번호 인코더
     * eGovFrame 5.0 Crypto Starter 의 EgovPasswordEncoder 와 호환
     * (BCrypt 사용 - ARIA 암호화가 필요한 경우 EgovPasswordEncoder 주입)
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
