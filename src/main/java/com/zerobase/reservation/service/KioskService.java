package com.zerobase.reservation.service;

import com.zerobase.reservation.domain.entity.Reservation;
import com.zerobase.reservation.domain.repository.ReservationRepository;
import com.zerobase.reservation.exception.CustomException;
import com.zerobase.reservation.exception.ErrorCode;
import com.zerobase.reservation.type.ReservationStatus;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KioskService {

    private final ReservationRepository reservationRepository;

    public Reservation checkReservation(Long restaurantId, String code) {

        // 1) code에 해당하는 reservation이 존재하는지 체크
        Reservation reservation = reservationRepository.findByCode(code)
            .orElseThrow(() -> new CustomException(ErrorCode.RESERVATION_NOT_FOUND));

        // 2) 매장이 일치한지
        if (restaurantId.intValue() != reservation.getRestaurant().getId().intValue()) {
            throw  new CustomException(ErrorCode.RESTAURANT_UN_MATCHED);
        }

        // 3) 예약 상태 확인
        if (reservation.getStatus() != ReservationStatus.COMPLETE) {
            throw new CustomException(ErrorCode.RESERVATION_NOT_COMPLETE);
        }

        // 4) 10전 도착하지 않았을 경우 - 에약 방문 시간 초과
        if (reservation.getReservationTime().isBefore(LocalDateTime.now().plusMinutes(10))) {
            throw new CustomException(ErrorCode.RESERVATION_TIME_OVER);
        }

        reservation.visit();

        return reservationRepository.save(reservation);
    }

}
