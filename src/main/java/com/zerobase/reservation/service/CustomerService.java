package com.zerobase.reservation.service;

import com.zerobase.reservation.configuration.jwt.JwtTokenProvider;
import com.zerobase.reservation.domain.entity.Customer;
import com.zerobase.reservation.domain.repository.CustomerRepository;
import com.zerobase.reservation.dto.CustomerCreateRequestDto;
import com.zerobase.reservation.dto.LogInForm;
import com.zerobase.reservation.exception.CustomException;
import com.zerobase.reservation.exception.ErrorCode;
import com.zerobase.reservation.type.Role;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public Customer signUp(CustomerCreateRequestDto customerCreateRequestDto) {

        // 이메일 중복 체크
        String email = customerCreateRequestDto.getEmail();

        if (isExistsByEmail(email)) {
            throw new CustomException(ErrorCode.USER_DUPLICATED_EMAIL);
        }

        return customerRepository.save(
            Customer.builder()
                .email(email)
                .name(customerCreateRequestDto.getName())
                .password(customerCreateRequestDto.getPassword())
                .phoneNumber(customerCreateRequestDto.getPhoneNumber())
                .role(Role.CUSTOMER)
                .build()
        );

    }

    @Transactional
    public String logIn(LogInForm form) {

        Customer customer = customerRepository.findByEmailAndPassword(
                form.getEmail(),
                form.getPassword())
            .orElseThrow(() -> new CustomException(ErrorCode.USER_LOGIN_FAIL));

        return jwtTokenProvider.createToken(customer.getEmail(), customer.getId(),
            customer.getRole());

    }

    private boolean isExistsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

}
