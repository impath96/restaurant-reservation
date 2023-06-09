package com.zerobase.reservation.controller;

import com.zerobase.reservation.dto.ReservationCheckRequestDto;
import com.zerobase.reservation.service.KioskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  키오스크 관련 Controller
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/kiosk")
public class KioskController {

    private final KioskService kioskService;

    /**
     * 예약 확인 및 방문 처리 메서드
     */
    @PostMapping("/check")
    public ResponseEntity<String> checkReservation(@RequestBody ReservationCheckRequestDto requestDto) {

        kioskService.checkReservation(requestDto.getRestaurantId(), requestDto.getCode());

        return ResponseEntity.ok("방문 확인 완료");
    }

}
