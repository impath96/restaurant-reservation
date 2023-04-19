package com.zerobase.reservation.exception;

public class OwnerNotPartnerException extends RuntimeException {

    public OwnerNotPartnerException() {
        super("점장이 아직 파트너 가입되어 있지 않습니다.");
    }

}
