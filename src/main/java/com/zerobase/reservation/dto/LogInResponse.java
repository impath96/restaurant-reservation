package com.zerobase.reservation.dto;

import lombok.Getter;

@Getter
public class LogInResponse {

    private final String token;

    public LogInResponse(final String token) {
        this.token = token;
    }

}
