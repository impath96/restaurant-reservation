package com.zerobase.reservation.domain.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "shops")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Restaurant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id")
    private Long id;

    @Column(name = "shop_name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String address;

    private String description;

    @Column(name = "open_time")
    private LocalDateTime openTime;

    @Column(name = "close_time")
    private LocalDateTime closeTime;

    @Column(name = "break_time")
    private LocalDateTime breakTime;

    @Column(name = "rest_day")
    private LocalDate restDay;

    @Column(name = "is_open")
    private boolean isOpen;


}
