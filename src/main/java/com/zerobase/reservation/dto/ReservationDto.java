package com.zerobase.reservation.dto;

import com.zerobase.reservation.domain.entity.Reservation;
import com.zerobase.reservation.type.ReservationStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDto {

    private Long reservationId;
    private LocalDateTime reservationTime;
    private ReservationStatus status;

    private Long customerId;
    private String customerName;

    private Long restaurantId;
    private String restaurantName;
    private String restaurantDescription;

    public static ReservationDto fromEntity(Reservation reservation) {

        return ReservationDto.builder()
            .reservationId(reservation.getId())
            .reservationTime(reservation.getReservationTime())
            .status(reservation.getStatus())
            .customerId(reservation.getCustomer().getId())
            .customerName(reservation.getCustomer().getName())
            .restaurantId(reservation.getRestaurant().getId())
            .restaurantName(reservation.getRestaurant().getName())
            .restaurantDescription(reservation.getRestaurant().getDescription())
            .build();

    }

}
