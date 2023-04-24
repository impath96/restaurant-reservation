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

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LogInController {

    private final CustomerService customerService;
    private final OwnerService ownerService;

    // LogInReponse에 @Getter를 사용하지 않으면 HttpMediaTypeNotAcceptableException 발생
    @PostMapping("/customers")
    public ResponseEntity<LogInResponse> customerLogIn(@RequestBody LogInForm logInForm) {
        return ResponseEntity.ok(new LogInResponse(customerService.logIn(logInForm)));
    }

    @PostMapping("/owners")
    public ResponseEntity<LogInResponse> ownerLogIn(@RequestBody LogInForm logInForm) {
        return ResponseEntity.ok(new LogInResponse(ownerService.logIn(logInForm)));
    }
}
