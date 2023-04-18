package com.zerobase.reservation.service;

import com.zerobase.reservation.domain.entity.Customer;
import com.zerobase.reservation.domain.repository.CustomerRepository;
import com.zerobase.reservation.dto.CustomerCreateRequestDto;
import com.zerobase.reservation.exception.DuplicatedEmailException;
import com.zerobase.reservation.type.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer signUp(CustomerCreateRequestDto customerCreateRequestDto) {

        // 이메일 중복 체크
        String email = customerCreateRequestDto.getEmail();

        if (customerRepository.existsByEmail(email)) {
            throw new DuplicatedEmailException(email);
        }

        Customer customer = Customer.builder()
            .email(customerCreateRequestDto.getEmail())
            .name(customerCreateRequestDto.getName())
            .password(customerCreateRequestDto.getPassword())
            .phoneNumber(customerCreateRequestDto.getPhoneNumber())
            .role(Role.CUSTOMER)
            .build();

        return customerRepository.save(customer);

    }
}
