package com.zerobase.reservation.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // USER
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
    USER_DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST,"이미 존재하는 이메일 입니다."),
    USER_LOGIN_FAIL(HttpStatus.BAD_REQUEST, "아이디 또는 비밀번호가 틀렸습니다."),
    OWNER_ALREADY_PARTNER(HttpStatus.BAD_REQUEST, "이미 파트너에 가입한 상태입니다."),
    OWNER_NOT_PARTNER(HttpStatus.BAD_REQUEST, "파트너에 가입되어 있지 않습니다."),
    USER_ROLE_NOT_OWNER(HttpStatus.BAD_REQUEST, "사용자 권한이 점장이 아닙니다."),

    // RESTAURANT
    RESTAURANT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 매장입니다."),
    RESTAURANT_OWNER_UN_MATCHED(HttpStatus.BAD_REQUEST, "해당 매장의 점장이 아닙니다."),
    RESTAURANT_UN_MATCHED(HttpStatus.BAD_REQUEST, "다른 매장입니다."),

    // RESERVATION
    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 예약입니다."),
    RESERVATION_TIME_OVER(HttpStatus.BAD_REQUEST, "예약 방문 예정 시간을 초과했습니다."),
    RESERVATION_NOT_WAITING_APPROVAL(HttpStatus.BAD_REQUEST, "예약 승인 상태가 아닙니다."),
    RESERVATION_NOT_COMPLETE(HttpStatus.BAD_REQUEST, "예약 완료 상태가 아닙니다.");

    private final HttpStatus status;
    private final String detail;

    ErrorCode(HttpStatus status, String description) {
        this.status = status;
        this.detail = description;
    }

}
