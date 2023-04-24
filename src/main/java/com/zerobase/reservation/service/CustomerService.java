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

    /**
     * 회원가입
     * - 1) 이미 가입한 회원인지 확인(이메일 중복을 기준)
     * - 2) 회원 등록 처리
     */
    @Transactional
    public Customer signUp(CustomerCreateRequestDto customerCreateRequestDto) {

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

    /**
     *  로그인 처리
     *  -1) 로그인 form에 대한 고객이 존재하는지 확인
     *    - 나의 경우, 이메일이 틀렸는지, 비밀번호가 틀렸는지는 알려주지 않고 단순히 로그인 실패로 처리
     *  -2) 토큰 발행
     *  추가할 부분 : 로그인 입력 정보에 대한 유효성 검사도 진행(이메일 형식, 비밀번호 길이 제한 등)
     */
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
