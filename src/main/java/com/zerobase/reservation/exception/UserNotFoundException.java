package com.zerobase.reservation.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String email) {
        super(String.format("(%s) 이메일에 해당하는 사람을 찾을 수 없습니다.", email));
    }
}
