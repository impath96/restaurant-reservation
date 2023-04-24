package com.zerobase.reservation.controller;

import com.zerobase.reservation.dto.LogInForm;
import com.zerobase.reservation.dto.LogInResponse;
import com.zerobase.reservation.service.CustomerService;
import com.zerobase.reservation.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 로그인 관련 Controller
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LogInController {

    private final CustomerService customerService;
    private final OwnerService ownerService;

    // LogInReponse에 @Getter를 사용하지 않으면 HttpMediaTypeNotAcceptableException 발생

    /**
     * 고객 로그인 처리
     */
    @PostMapping("/customers")
    public ResponseEntity<LogInResponse> customerLogIn(@RequestBody LogInForm logInForm) {
        return ResponseEntity.ok(new LogInResponse(customerService.logIn(logInForm)));
    }


    /**
     * 점장 로그인 처리
     */
    @PostMapping("/owners")
    public ResponseEntity<LogInResponse> ownerLogIn(@RequestBody LogInForm logInForm) {
        return ResponseEntity.ok(new LogInResponse(ownerService.logIn(logInForm)));
    }
}
