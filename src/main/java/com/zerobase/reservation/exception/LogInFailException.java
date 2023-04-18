package com.zerobase.reservation.exception;

public class LogInFailException extends RuntimeException {

    public LogInFailException() {
        super("아이디 또는 비밀번호가 틀렸습니다.");
    }
}
