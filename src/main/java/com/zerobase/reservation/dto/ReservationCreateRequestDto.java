package com.zerobase.reservation.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationCreateRequestDto {

    private Long restaurantId;

    @DateTimeFormat(iso = ISO.DATE_TIME)
    private LocalDateTime reservationTime;

}
