package com.zerobase.reservation.dto;

import com.zerobase.reservation.type.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class User {

    private Long id;
    private String email;
    private Role role;

}
