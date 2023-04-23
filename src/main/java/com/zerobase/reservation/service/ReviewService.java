package com.zerobase.reservation.service;

import com.zerobase.reservation.domain.entity.Reservation;
import com.zerobase.reservation.domain.entity.Review;
import com.zerobase.reservation.domain.repository.ReservationRepository;
import com.zerobase.reservation.domain.repository.ReviewRepository;
import com.zerobase.reservation.dto.ReviewCreateRequestDto;
import com.zerobase.reservation.exception.CustomException;
import com.zerobase.reservation.exception.ErrorCode;
import com.zerobase.reservation.type.ReservationStatus;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public Review writeReview(Long customerId, ReviewCreateRequestDto requestDto) {

        Reservation reservation = reservationRepository
            .findFirstByCustomerIdOrderByReservationTimeDesc(customerId)
            .orElseThrow(() -> new CustomException(ErrorCode.RESERVATION_NOT_FOUND));

        if (!(reservation.getStatus() == ReservationStatus.COMPLETE || reservation.getStatus() == ReservationStatus.VISITED)) {
            throw new RuntimeException("방문한 사람만 리뷰 작성이 가능합니다.");
        }

        return reviewRepository.save(
            Review.builder()
                .content(requestDto.getContent())
                .score(requestDto.getScore())
                .customer(reservation.getCustomer())
                .restaurant(reservation.getRestaurant())
                .build()
        );
    }
}
