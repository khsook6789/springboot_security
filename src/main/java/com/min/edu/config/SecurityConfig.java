package com.min.edu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * ================================================================
 * Spring Security 설정 클래스 (학습용 가이드)
 * ================================================================
 *
 * [클래스 선언 어노테이션]
 *   @Configuration      - 스프링 설정 클래스 등록
 *   @EnableWebSecurity  - Spring Security 활성화 (Spring Boot는 자동 활성화되므로 생략 가능)
 *
 * ================================================================
 * 1. SecurityFilterChain - HTTP 보안 규칙 설정
 * ================================================================
 *
 *   @Bean
 *   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
 *
 *       // [1-1] URL별 접근 권한 설정
 *       http.authorizeHttpRequests(auth -> auth
 *           .requestMatchers("/public/**").permitAll()          // 인증 없이 허용
 *           .requestMatchers("/admin/**").hasRole("ADMIN")      // ADMIN 역할만 허용
 *           .anyRequest().authenticated()                       // 나머지는 로그인 필요
 *       );
 *
 *       // [1-2] 인증 방식 - HTTP Basic Auth (Postman Authorization 탭 → Basic Auth)
 *       http.httpBasic(Customizer.withDefaults());
 *
 *       // [1-3] CSRF 설정
 *       // Postman으로 POST 테스트 시 CSRF 토큰이 없어서 403 발생 → 본 프로젝트는 학습용이므로 비활성화
 *       http.csrf(csrf -> csrf.disable());
 *
 *       // [1-4] 세션 관리
 *       // import org.springframework.security.config.http.SessionCreationPolicy;
 *       // http.sessionManagement(session -> session
 *       //     .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // JWT 방식에서 사용
 *       // );
 *
 *       // [1-5] 로그아웃 설정
 *       // http.logout(logout -> logout
 *       //     .logoutUrl("/logout")
 *       //     .logoutSuccessUrl("/public/hello")
 *       //     .invalidateHttpSession(true)
 *       //     .deleteCookies("JSESSIONID")
 *       // );
 *
 *       // [1-6] 예외 처리 (403 Forbidden 핸들러 등)
 *       // http.exceptionHandling(ex -> ex
 *       //     .accessDeniedPage("/access-denied")
 *       // );
 *
 *       return http.build();
 *   }
 *
 * ================================================================
 * [참고] Spring Security 필터 체인 동작 순서
 * ================================================================
 *
 *   HTTP 요청
 *     → SecurityFilterChain (여러 Filter 순차 실행)
 *         → BasicAuthenticationFilter         (HTTP Basic 처리)
 *         → AuthorizationFilter               (URL 권한 검사)
 *     → DispatcherServlet → Controller
 *
 * ================================================================
 * [참고] Postman 테스트 시나리오
 * ================================================================
 *
 *   시나리오 1. HTTP Basic Auth
 *     GET http://localhost:8080/private/hello
 *     Authorization 탭 → Type: Basic Auth → username/password 입력
 *
 *   시나리오 2. 권한 없음 확인
 *     GET http://localhost:8080/admin/hello
 *     USER 계정으로 접근 시 403 Forbidden 응답 확인
 *
 * ================================================================
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 아래에 학습용으로 SecurityConfig 작성해보기
    // 아래 예시 내용 지우고 자유롭게 해보면 됨

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // [1-1] URL별 접근 권한 설정
        http.authorizeHttpRequests(auth -> auth
            .requestMatchers("/public/**").permitAll()       // 인증 없이 허용
            .requestMatchers("/admin/**").hasRole("ADMIN")   // ADMIN 역할만 허용
            .anyRequest()                    // 나머지는 로그인 필요
        );

        // [1-2] 인증 방식 - HTTP Basic Auth (Postman Authorization 탭 → Basic Auth)
        http.httpBasic(Customizer.withDefaults());

        // [1-3] CSRF 비활성화 - Postman POST 테스트 시 CSRF 토큰 없어서 403 발생 방지
        http.csrf(csrf -> csrf.disable());

        return http.build();
    }

}
