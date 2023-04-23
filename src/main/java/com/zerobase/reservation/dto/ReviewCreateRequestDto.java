package com.zerobase.reservation.dto;

import lombok.Getter;

@Getter
public class ReviewCreateRequestDto {

    private Long restaurantId;

    private String content;
    private Integer score;

}
