package com.zerobase.reservation.type;

import lombok.Getter;

@Getter
public enum Role {

    CUSTOMER("고객"),
    OWNER("점장");

    private final String role;

    Role(final String role) {
        this.role = role;
    }

}
