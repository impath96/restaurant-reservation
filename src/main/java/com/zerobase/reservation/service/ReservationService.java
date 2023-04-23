package com.zerobase.reservation.service;

import com.zerobase.reservation.domain.entity.Customer;
import com.zerobase.reservation.domain.entity.Reservation;
import com.zerobase.reservation.domain.entity.Restaurant;
import com.zerobase.reservation.domain.repository.CustomerRepository;
import com.zerobase.reservation.domain.repository.ReservationRepository;
import com.zerobase.reservation.domain.repository.RestaurantRepository;
import com.zerobase.reservation.exception.ReservationNotFoundException;
import com.zerobase.reservation.exception.RestaurantNotFoundException;
import com.zerobase.reservation.exception.UserNotFoundException;
import com.zerobase.reservation.type.ReservationStatus;
import java.time.LocalDateTime;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final ReservationRepository reservationRepository;

    private static final int CODE_LENGTH = 10;

    // 예약 하기
    @Transactional
    public Reservation makeReservation(String customerEmail, Long restaurantId,
        LocalDateTime reservationTime) {

        Customer customer = customerRepository.findByEmail(customerEmail)
            .orElseThrow(() -> new UserNotFoundException(customerEmail));

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
            .orElseThrow(() -> new RestaurantNotFoundException());

        return reservationRepository.save(
            Reservation.builder()
                .customer(customer)
                .restaurant(restaurant)
                .reservationTime(reservationTime)
                .code(generateCode())
                .status(ReservationStatus.WAITING_APPROVAL)
                .customerName(customer.getName())
                .build()
        );

    }

    @Transactional
    public List<Reservation> getAllReservationByCustomerId(Long customerId) {

        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new UserNotFoundException(""));

        return reservationRepository.findAllByCustomerId(customer.getId());
    }

    @Transactional
    public List<Reservation> getAllReservation(Long restaurantId) {
        return reservationRepository.findAllByRestaurantId(restaurantId);
    }

    @Transactional
    public void cancelReservation(Long customerId, Long reservationId) {

        // 예약 존재하는지 확인
        Reservation reservation = reservationRepository.findByIdAndCustomerId(reservationId,
                customerId)
            .orElseThrow(() -> new ReservationNotFoundException());

        reservation.cancel();
        reservationRepository.save(reservation);

    }

    // 10자리 랜덤 문자(알파벳 + 숫자)
    private String generateCode() {
        return RandomStringUtils.randomAlphabetic(CODE_LENGTH);
    }
}
