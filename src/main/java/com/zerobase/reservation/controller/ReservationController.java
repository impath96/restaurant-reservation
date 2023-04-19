package com.zerobase.reservation.controller;

import com.zerobase.reservation.configuration.jwt.JwtTokenProvider;
import com.zerobase.reservation.dto.ReservationCreateRequestDto;
import com.zerobase.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private static final String AUTH_TOKEN = "X-AUTH-TOKEN";
    private final JwtTokenProvider jwtTokenProvider;

    private final ReservationService reservationService;

    // customer - 내 예약 정보
    // customer - 예약 취소
    // admin    - 예약 조회(모든 예약 조회, 특정 예약 상태 조회 등등)
    // admin    - 예약 승인, 거절

    @PostMapping("/restaurants/{restaurantId}/reservations")
    public ResponseEntity<String> makeReservation(
        @RequestHeader(name = AUTH_TOKEN) String token,
        @PathVariable Long restaurantId,
        @RequestBody ReservationCreateRequestDto requestDto
    ) {

        String customerEmail = jwtTokenProvider.getEmail(token);

        reservationService.makeReservation(customerEmail, restaurantId,
            requestDto.getReservationTime());

        return ResponseEntity.ok("예약 완료");
    }

}
