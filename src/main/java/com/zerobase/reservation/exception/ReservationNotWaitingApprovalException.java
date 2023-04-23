package com.zerobase.reservation.exception;

public class ReservationNotWaitingApprovalException extends RuntimeException{

    public ReservationNotWaitingApprovalException() {
        super("예약 승인 상태가 아닙니다.");
    }

}
