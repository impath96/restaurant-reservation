package com.zerobase.reservation.controller;

import com.zerobase.reservation.dto.LogInForm;
import com.zerobase.reservation.dto.OwnerCreateRequestDto;
import com.zerobase.reservation.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/owners")
public class OwnerController {

    private final OwnerService ownerService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody OwnerCreateRequestDto requestDto) {

        ownerService.signUp(requestDto);

        return ResponseEntity.ok("회원 가입 완료");
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> logIn(@RequestBody LogInForm form) {
        return ResponseEntity.ok(ownerService.logIn(form));
    }
}
