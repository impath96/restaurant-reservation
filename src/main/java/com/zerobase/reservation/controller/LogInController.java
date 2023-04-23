package com.zerobase.reservation.controller;

import com.zerobase.reservation.dto.LogInForm;
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

    @PostMapping("/customers")
    public ResponseEntity<String> customerLogIn(@RequestBody LogInForm logInForm) {
        return ResponseEntity.ok(customerService.logIn(logInForm));
    }

    @PostMapping("/owners")
    public ResponseEntity<String> ownerLogIn(@RequestBody LogInForm logInForm) {
        return ResponseEntity.ok(ownerService.logIn(logInForm));
    }

}
