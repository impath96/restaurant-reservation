package com.zerobase.reservation.exception;

public class UnMatchedRestaurantException extends RuntimeException{

    public UnMatchedRestaurantException() {
        super("다른 매장 입니다.");
    }

}
