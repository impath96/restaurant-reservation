package com.zerobase.reservation.domain.repository;

import com.zerobase.reservation.domain.entity.Reservation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByCustomerId(Long customerId);
    List<Reservation> findAllByRestaurantId(Long restaurantId);
    Optional<Reservation> findByIdAndCustomerId(Long reservationId, Long customerId);

    Optional<Reservation> findByCode(String code);
}
