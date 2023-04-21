package com.zerobase.reservation.domain.entity;

import com.zerobase.reservation.type.Role;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "customers")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Customer extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

}
