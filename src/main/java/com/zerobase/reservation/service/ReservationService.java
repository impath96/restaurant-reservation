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
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationValidator reservationValidator;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final ReservationRepository reservationRepository;

    private static final int CODE_LENGTH = 10;

    /**
     * 예약하기
     * - 가장 최근 에약 정보 기준
     * - 예약 정보가 없을 경우 : 예약 시간 유효성 검사 후 예약 진행
     * - 예약 정보가 있을 경우 : 예약 취소, 거절 상태일 경우 해당 시점부터 1시간 이후에 예약 가능하도록
     * - 추가할 내용 : 최근 예약 정보를 예약 당일 기준으로 변경하기
     */
    @Transactional
    public Reservation makeReservation(Long customerId, Long restaurantId,
        LocalDateTime reservationTime) {

        Customer customer = findCustomerById(customerId);
        Restaurant restaurant = findRestaurantById(restaurantId);

        Optional<Reservation> customerReservation = reservationRepository
            .findAllByCustomerId(customerId)
            .stream()
            .sorted(Comparator.comparing(Reservation::getReservationTime).reversed())
            .findFirst();

        if (customerReservation.isPresent()) {
            ReservationStatus status = customerReservation.get().getStatus();
            LocalDateTime updatedAt = customerReservation.get().getUpdatedAt();

            if ((status == ReservationStatus.REJECTED
                || status == ReservationStatus.CANCEL)
                && LocalDateTime.now().isBefore(updatedAt.plusHours(1))) {
                throw new CustomException(ErrorCode.RESERVATION_IMPOSSIBLE);
            }
        }

        Reservation reservation = Reservation.builder()
            .customer(customer)
            .restaurant(restaurant)
            .reservationTime(reservationTime)
            .code(generateCode())
            .status(ReservationStatus.WAITING_APPROVAL)
            .customerName(customer.getName())
            .build();

        reservationValidator.validate(reservation);

        return reservationRepository.save(reservation);


    }

    /**
     * 내 예약 정보 모두 보기(고객 용)
     */
    @Transactional
    public List<Reservation> getAllReservationByCustomerId(Long userId) {
        Customer customer = findCustomerById(userId);
        return reservationRepository.findAllByCustomerId(customer.getId());
    }

    /**
     * 특정 매장의 예약 모두 보기(점장 용)
     */
    @Transactional
    public List<Reservation> getAllReservation(Long restaurantId) {
        return reservationRepository.findAllByRestaurantId(restaurantId);
    }

    /**
     * 예약 취소(고객)
     */
    @Transactional
    public void cancelReservation(Long customerId, Long reservationId) {

        Reservation reservation = findReservationById(reservationId);

        if (reservation.getCustomer().getId().equals(customerId)) {
            throw new CustomException(ErrorCode.RESERVATION_CUSTOMER_UN_MATCHED);
        }

        reservation.cancel();

        reservationRepository.save(reservation);

    }

    /**
     * 예약 거절(점장 용)
     */
    @Transactional
    public void rejectReservation(Long ownerId, Long reservationId) {

        Reservation reservation = findReservationById(reservationId);

        validate(reservation, ownerId);

        reservation.reject();

        reservationRepository.save(reservation);
    }

    /**
     * 예약 승인(점장 용)
     */
    @Transactional
    public Reservation approveReservation(Long ownerId, Long reservationId) {

        Reservation reservation = findReservationById(reservationId);

        validate(reservation, ownerId);

        reservation.approve();

        return reservationRepository.save(reservation);
    }

    // 예약을 거절, 승인을 위해 해당 매장의 점장인지, 예약 상태가 승인 대기중인지 확인하는 메서드
    private void validate(Reservation reservation, Long ownerId) {

        Restaurant restaurant = reservation.getRestaurant();

        // 해당 매장의 점장이 아닐 경우
        if (!restaurant.isOwner(ownerId)) {
            throw new CustomException(ErrorCode.RESTAURANT_OWNER_UN_MATCHED);
        }

        // 이미 예약 완료되었을 경우
        if (reservation.getStatus() == ReservationStatus.COMPLETE) {
            throw new CustomException(ErrorCode.RESERVATION_ALREADY_COMPLETE);
        }

        // 이미 예약 취소한 경우
        if (reservation.getStatus() == ReservationStatus.CANCEL) {
            throw new CustomException(ErrorCode.RESERVATION_ALREADY_COMPLETE);
        }

        // 그외의 경우에는 어떻게 처리할까...?
        if (reservation.getStatus() != ReservationStatus.WAITING_APPROVAL) {
            throw new CustomException(ErrorCode.RESERVATION_NOT_WAITING_APPROVAL);
        }

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

    // 10자리 랜덤 문자(알파벳 + 숫자)
    private String generateCode() {
        return RandomStringUtils.randomAlphabetic(CODE_LENGTH);
    }

}
