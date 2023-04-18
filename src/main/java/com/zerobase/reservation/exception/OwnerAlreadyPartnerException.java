package com.zerobase.reservation.exception;

public class OwnerAlreadyPartnerException extends RuntimeException{

    public OwnerAlreadyPartnerException() {
        super("이미 파트너에 가입되어 있는 상태입니다.");
    }

}
