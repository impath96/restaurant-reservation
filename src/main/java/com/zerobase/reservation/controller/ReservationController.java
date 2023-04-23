package com.zerobase.reservation.controller;

import com.zerobase.reservation.configuration.jwt.JwtTokenProvider;
import com.zerobase.reservation.domain.entity.Reservation;
import com.zerobase.reservation.dto.ReservationCreateRequestDto;
import com.zerobase.reservation.dto.ReservationDto;
import com.zerobase.reservation.service.ReservationService;
import com.zerobase.reservation.type.Role;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReservationController {

    private static final String AUTH_TOKEN = "X-AUTH-TOKEN";
    private final JwtTokenProvider jwtTokenProvider;

    private final ReservationService reservationService;

    // customer - 내 예약 정보
    // customer - 예약 취소
    // owner    - 예약 조회(모든 예약 조회, 특정 예약 상태 조회 등등)
    // owner    - 예약 승인, 거절

    // 예약 하기
    // 고민할 부분 : 예약 시 회원 가입 유무를 Filter 활용해야 하나?
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

    // customer 예약 정보
    @GetMapping("/customers/reservations")
    public ResponseEntity<List<ReservationDto>> getAllCustomerReservations(@RequestHeader(name = AUTH_TOKEN) String token) {

        List<Reservation> reservations = reservationService.getAllReservationByCustomerId(
            jwtTokenProvider.getId(token));

        return ResponseEntity.ok(
            reservations.stream()
                .map(ReservationDto::fromEntity)
                .collect(Collectors.toList())
        );
    }

    // 특정 매장 모든 예약 정보(점장 용)
    @GetMapping("/restaurants/{restaurantId}/reservations")
    public ResponseEntity<List<ReservationDto>> getAllReservations(@PathVariable Long restaurantId) {

        List<Reservation> reservations = reservationService.getAllReservation(restaurantId);

        return ResponseEntity.ok(
            reservations.stream()
                .map(ReservationDto::fromEntity)
                .collect(Collectors.toList())
        );
    }

    // DELETE로 해야하나? 예약 취소
    @PutMapping("/reservations/cancel")
    public ResponseEntity<String> cancelReservation(
        @RequestHeader(name = AUTH_TOKEN) String token,
        @RequestBody Long reservationId
    ) {

        Long customerId = jwtTokenProvider.getId(token);

        reservationService.cancelReservation(customerId, reservationId);

        return ResponseEntity.ok("예약 취소 완료");

    }

    // 예약 거절
    @PutMapping("/reservations/reject")
    public ResponseEntity<String> rejectReservation(
        @RequestHeader(name = AUTH_TOKEN) String token,
        @RequestParam Long reservationId
    ) {

        if (jwtTokenProvider.getRole(token) != Role.OWNER) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("점장만 접근이 가능합니다.");
        }

        Long ownerId = jwtTokenProvider.getId(token);
        reservationService.rejectReservation(ownerId, reservationId);

        return ResponseEntity.ok("예약 거절");
    }

    // 예약 승인
    @PutMapping("/reservations/approve")
    public ResponseEntity<String> approveReservation(
        @RequestHeader(name = AUTH_TOKEN) String token,
        @RequestParam Long reservationId
    ) {

        if (jwtTokenProvider.getRole(token) != Role.OWNER) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("점장만 접근이 가능합니다.");
        }

        Long ownerId = jwtTokenProvider.getId(token);
        reservationService.approveReservation(ownerId, reservationId);

        return ResponseEntity.ok("예약 승인");
    }


}
