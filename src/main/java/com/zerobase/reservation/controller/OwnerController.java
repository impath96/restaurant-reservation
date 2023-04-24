package com.zerobase.reservation.controller;

import com.zerobase.reservation.configuration.jwt.JwtTokenProvider;
import com.zerobase.reservation.domain.entity.Owner;
import com.zerobase.reservation.dto.User;
import com.zerobase.reservation.exception.CustomException;
import com.zerobase.reservation.exception.ErrorCode;
import com.zerobase.reservation.service.OwnerService;
import com.zerobase.reservation.type.Role;
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
    // 나중에 Jwt 용 User 객체를 만들기
    @PutMapping("/partner")
    public ResponseEntity<String> registerPartner(@RequestHeader(name = AUTH_TOKEN) String token) {

        User user = jwtTokenProvider.getUser(token);

        // 회원 권한 체크
        if (!isOwnerRole(user.getRole())) {
            throw new CustomException(ErrorCode.USER_ROLE_NOT_OWNER);
        }

        Owner owner = ownerService.registerPartner(user.getId());

        // 메시지 만드는 메서드 따로 추출 하기 or 파트너 가입에 대한 Response 객체 만들기
        return ResponseEntity.ok(owner.getName() + "님, 파트너 가입이 완료되었습니다.");
    }

    // 권한이 Owner 인지 체크
    private boolean isOwnerRole(Role role) {
        return role == Role.OWNER;
    }

    // 점장 정보 수정
    // 점장 정보(내 정보)
    // 회원 탈퇴
}
