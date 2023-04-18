package com.zerobase.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCreateRequestDto {

    private String email;
    private String password;
    private String phoneNumber;
    private String name;

}
