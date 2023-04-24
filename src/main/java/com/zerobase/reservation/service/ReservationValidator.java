package com.zerobase.reservation.service;

import com.zerobase.reservation.domain.entity.Reservation;
import com.zerobase.reservation.domain.entity.Restaurant;
import com.zerobase.reservation.exception.CustomException;
import com.zerobase.reservation.exception.ErrorCode;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class ReservationValidator {

    private static final int MIN_RESERVATION_AVAILABLE_TIME = 30;

    public void validate(Reservation reservation) {

        validate(reservation, reservation.getRestaurant());

    }

    void validate(Reservation reservation, Restaurant restaurant) {

        if (!restaurant.isOpen()) {
            throw new IllegalArgumentException("매장이 영업중이 아닙니다.");
        }

        if (!isPossibleTime(reservation.getReservationTime())) {
            throw new CustomException(ErrorCode.INVALID_RESERVATION_TIME);
        }

    }

    private boolean isPossibleTime(LocalDateTime reservationTime) {
        return !reservationTime.isBefore(
            LocalDateTime.now().plusMinutes(MIN_RESERVATION_AVAILABLE_TIME)
        );
    }
}
