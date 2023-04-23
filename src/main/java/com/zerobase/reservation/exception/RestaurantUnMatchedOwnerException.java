package com.zerobase.reservation.exception;

public class RestaurantUnMatchedOwnerException extends RuntimeException {

    public RestaurantUnMatchedOwnerException() {
        super("해당 매장의 점장이 아닙니다.");
    }

}
