package com.zerobase.reservation.controller;

import com.zerobase.reservation.dto.CustomerCreateRequestDto;
import com.zerobase.reservation.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    // 회원 가입
    @PostMapping
    public ResponseEntity<String> signUp(CustomerCreateRequestDto requestDto) {

        customerService.signUp(requestDto);

        return ResponseEntity.ok("회원 가입 완료");
    }

}
