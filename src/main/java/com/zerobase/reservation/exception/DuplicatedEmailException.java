package com.zerobase.reservation.exception;

public class DuplicatedEmailException extends RuntimeException {

    public DuplicatedEmailException(String email) {
        super(String.format("이미 존재하는 이메일 입니다 : %s", email));
    }
}
