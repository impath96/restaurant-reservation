package com.zerobase.reservation.service;

import com.zerobase.reservation.domain.entity.Customer;
import com.zerobase.reservation.domain.entity.Reservation;
import com.zerobase.reservation.domain.entity.Restaurant;
import com.zerobase.reservation.domain.repository.CustomerRepository;
import com.zerobase.reservation.domain.repository.ReservationRepository;
import com.zerobase.reservation.domain.repository.RestaurantRepository;
import com.zerobase.reservation.exception.CustomException;
import com.zerobase.reservation.exception.ErrorCode;
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
    private static final int MIN_RESERVATION_AVAILABLE_TIME = 30;

    // 예약 하기
    @Transactional
    public Reservation makeReservation(Long customerId, Long restaurantId,
        LocalDateTime reservationTime) {

        Customer customer = findCustomerById(customerId);
        Restaurant restaurant = findRestaurantById(restaurantId);

        // 1) 예약 시간이 현재 시간보다 이전인지 체크
        // 2) 최소 예약 가능 시간 : 현재 시간 + 30분으로 설정
        if (!isValidReservationTime(reservationTime)) {
            throw new CustomException(ErrorCode.INVALID_RESERVATION_TIME);
        }

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
    public List<Reservation> getAllReservationByCustomerId(Long userId) {

        Customer customer = findCustomerById(userId);
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
            .orElseThrow(() -> new CustomException(ErrorCode.RESERVATION_NOT_FOUND));

        reservation.cancel();

        reservationRepository.save(reservation);

    }

    @Transactional
    public void rejectReservation(Long ownerId, Long reservationId) {

        Reservation reservation = findReservationById(reservationId);

        // 해당 매장의 점장이 아닐 경우
        if (!reservation.getRestaurant().getOwner().getId().equals(ownerId)) {
            throw new CustomException(ErrorCode.RESTAURANT_OWNER_UN_MATCHED);
        }

        // 예약 상태 확인
        if (reservation.getStatus() != ReservationStatus.WAITING_APPROVAL) {
            throw new CustomException(ErrorCode.RESERVATION_NOT_WAITING_APPROVAL);
        }

        reservation.reject();
        reservationRepository.save(reservation);
    }

    @Transactional
    public Reservation approveReservation(Long ownerId, Long reservationId) {

        Reservation reservation = findReservationById(reservationId);

        // 해당 매장의 점장이 아닐 경우
        if (!reservation.getRestaurant().getOwner().getId().equals(ownerId)) {
            throw new CustomException(ErrorCode.RESTAURANT_OWNER_UN_MATCHED);
        }

        if (reservation.getStatus() != ReservationStatus.WAITING_APPROVAL) {
            throw new CustomException(ErrorCode.RESERVATION_NOT_WAITING_APPROVAL);
        }

        reservation.approve();
        return reservationRepository.save(reservation);
    }

    private Restaurant findRestaurantById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
            .orElseThrow(() -> new CustomException(ErrorCode.RESTAURANT_NOT_FOUND));
    }

    private Reservation findReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId)
            .orElseThrow(() -> new CustomException(ErrorCode.RESERVATION_NOT_FOUND));
    }

    private Customer findCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    private boolean isValidReservationTime(LocalDateTime reservationTime) {
        if (reservationTime.isBefore(LocalDateTime.now())) {
            return false;
        }
        return !reservationTime.isBefore(LocalDateTime.now().plusMinutes(
            MIN_RESERVATION_AVAILABLE_TIME));
    }

    // 10자리 랜덤 문자(알파벳 + 숫자)
    private String generateCode() {
        return RandomStringUtils.randomAlphabetic(CODE_LENGTH);
    }

}
