package com.zerobase.reservation.exception;

public class ReservationVisitTimeOverException extends RuntimeException{

    public ReservationVisitTimeOverException() {
        super("예약 방문 예정 시간 초과입니다.");
    }

}
