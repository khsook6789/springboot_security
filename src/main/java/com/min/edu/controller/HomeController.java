package com.min.edu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Postman 테스트용 간소화 컨트롤러
 *
 * 엔드포인트 구성:
 *   GET /public/hello  - 인증 없이 접근 가능 (SecurityConfig에서 permitAll 설정)
 *   GET /private/hello - 로그인한 사용자만 접근 (SecurityConfig에서 authenticated 설정)
 *   GET /admin/hello   - ADMIN 역할만 접근 (SecurityConfig에서 hasRole("ADMIN") 설정)
 *
 * Postman 테스트 방법:
 *   - HTTP Basic Auth: Authorization 탭 → Basic Auth → username/password 입력
 *
 * [시나리오 1] 공개 URL - 인증 없이 접근
 *   GET http://localhost:8080/public/hello
 *   Auth : None
 *   예상 : 200 OK
 *
 * [시나리오 2] 인증 없이 보호 URL 접근
 *   GET http://localhost:8080/private/hello
 *   Auth : None
 *   예상 : 401 Unauthorized
 *
 * [시나리오 3] HTTP Basic Auth 로그인
 *   GET http://localhost:8080/private/hello
 *   Auth : Authorization 탭 → Basic Auth → Username: user / Password: 1234
 *   예상 : 200 OK
 *
 * [시나리오 4] 권한 없음 확인 (403)
 *   GET http://localhost:8080/admin/hello
 *   Auth : Authorization 탭 → Basic Auth → Username: user / Password: 1234
 *   예상 : 403 Forbidden (user 계정은 ROLE_USER만 보유, ROLE_ADMIN 없어서 접근 거부)
 */
@RestController
public class HomeController {

    // 누구나 접근 가능
    @GetMapping("/public/hello")
    public ResponseEntity<String> publicHello() {
        return ResponseEntity.ok("공개 영역이다. 인증 없이 접근 가능합니다.");
    }

    // 로그인한 사용자만 접근 가능
    @GetMapping("/private/hello")
    public ResponseEntity<String> privateHello(Principal principal) {
        return ResponseEntity.ok("비공개 영역입니다. 현재 로그인 사용자: " + principal.getName());
    }

    // ADMIN 역할만 접근 가능
    @GetMapping("/admin/hello")
    public ResponseEntity<String> adminHello(Principal principal) {
        return ResponseEntity.ok("관리자 영역입니다. 현재 로그인 사용자: " + principal.getName());
    }

}
