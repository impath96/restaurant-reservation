package com.zerobase.reservation.type;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Role {

    CUSTOMER("고객"),
    OWNER("점장");

    private final String role;

    Role(final String role) {
        this.role = role;
    }

    public static Role valueOfRole(String role) {
        return Arrays.stream(values())
            .filter(value -> value.role.equals(role))
            .findAny()
            .orElse(null);
    }
}
