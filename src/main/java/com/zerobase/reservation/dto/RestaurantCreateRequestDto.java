package com.zerobase.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantCreateRequestDto {

    private String name;
    private String phoneNumber;
    private String address;
    private String description;

}
