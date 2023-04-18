package com.zerobase.reservation.exception;

public class RestaurantNotFoundException extends RuntimeException {

    public RestaurantNotFoundException() {
        super("해당 매장을 찾지 못했습니다.");
    }
}
