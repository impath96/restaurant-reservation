package com.zerobase.reservation.exception;

public class ReservationNotCompleteException extends RuntimeException{

    public ReservationNotCompleteException() {
        super("예약 완료 상태가 아닙니다.");
    }

}
