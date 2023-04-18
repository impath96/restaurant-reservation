package com.zerobase.reservation.controller;

import com.zerobase.reservation.dto.CustomerCreateRequestDto;
import com.zerobase.reservation.dto.LogInForm;
import com.zerobase.reservation.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private static final String AUTH_TOKEN = "X-AUTH-TOKEN";
    private final CustomerService customerService;

    // 회원 가입
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody CustomerCreateRequestDto requestDto) {

        customerService.signUp(requestDto);

        return ResponseEntity.ok("회원 가입 완료");
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> logIn(@RequestBody LogInForm form) {
        return ResponseEntity.ok(customerService.logIn(form));
    }

}
