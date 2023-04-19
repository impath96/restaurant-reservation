package com.zerobase.reservation.service;

import com.zerobase.reservation.domain.entity.Customer;
import com.zerobase.reservation.domain.entity.Reservation;
import com.zerobase.reservation.domain.entity.Restaurant;
import com.zerobase.reservation.domain.repository.CustomerRepository;
import com.zerobase.reservation.domain.repository.ReservationRepository;
import com.zerobase.reservation.domain.repository.RestaurantRepository;
import com.zerobase.reservation.dto.ReservationCreateRequestDto;
import com.zerobase.reservation.exception.RestaurantNotFoundException;
import com.zerobase.reservation.exception.UserNotFoundException;
import com.zerobase.reservation.type.ReservationStatus;
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

    @Transactional
    public Reservation makeReservation(String customerEmail, ReservationCreateRequestDto requestDto) {

        Customer customer = customerRepository.findByEmail(customerEmail)
            .orElseThrow(() -> new UserNotFoundException(customerEmail));

        Long restaurantId = requestDto.getRestaurantId();
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
            .orElseThrow(() -> new RestaurantNotFoundException());

        Reservation reservation = Reservation.builder()
            .customer(customer)
            .restaurant(restaurant)
            .reservationTime(requestDto.getReservationTime())
            .code(generateCode())
            .status(ReservationStatus.WAITING_APPROVAL)
            .customerName(customer.getName())
            .build();

        return reservationRepository.save(reservation);

    }

    private String generateCode() {
        return RandomStringUtils.randomAlphabetic(CODE_LENGTH);
    }
}
