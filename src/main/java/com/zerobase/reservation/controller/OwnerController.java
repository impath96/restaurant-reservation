package com.zerobase.reservation.controller;

import com.zerobase.reservation.configuration.jwt.JwtTokenProvider;
import com.zerobase.reservation.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/owners")
public class OwnerController {

    private static final String AUTH_TOKEN = "X-AUTH-TOKEN";
    private final JwtTokenProvider jwtTokenProvider;
    private final OwnerService ownerService;

    // 파트너 가입
    @PutMapping("/partner")
    public ResponseEntity<String> registerPartner(@RequestHeader(name = AUTH_TOKEN) String token) {
        ownerService.registerPartner(jwtTokenProvider.getEmail(token));
        return ResponseEntity.ok("파트너 가입 완료");
    }

    // 점장 정보 수정
    // 점장 정보(내 정보)
    // 회원 탈퇴
}
