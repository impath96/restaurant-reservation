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

    /**
     *  예약 확인
     *  -1) 입력받은 code에 해당하는 예약 정보가 있는지 확인
     *  -2) 예약한 식당이 맞는지 확인
     *  -3) 예약 상태 확인(승인 완료의 경우에만 정상 처리)
     *  -4) 10분 전 방문 여부 확인
     *
     */
    public Reservation checkReservation(Long restaurantId, String code) {

        Reservation reservation = reservationRepository.findByCode(code)
            .orElseThrow(() -> new CustomException(ErrorCode.RESERVATION_NOT_FOUND));

        validate(restaurantId, reservation);

        reservation.visit();

        return reservationRepository.save(reservation);
    }

    private void validate(Long restaurantId, Reservation reservation) {

        if (!reservation.getRestaurant().getId().equals(restaurantId)) {
            throw new CustomException(ErrorCode.RESTAURANT_UN_MATCHED);
        }

        if (reservation.getStatus() != ReservationStatus.COMPLETE) {
            throw new CustomException(ErrorCode.RESERVATION_NOT_COMPLETE);
        }

        if (reservation.getReservationTime().isBefore(LocalDateTime.now().plusMinutes(10))) {
            reservation.timeOver();
            reservationRepository.save(reservation);
            throw new CustomException(ErrorCode.RESERVATION_TIME_OVER);
        }
    }

}
