package com.zerobase.reservation.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import com.zerobase.reservation.domain.entity.Customer;
import com.zerobase.reservation.domain.repository.CustomerRepository;
import com.zerobase.reservation.dto.CustomerCreateRequestDto;
import com.zerobase.reservation.type.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    @Test
    @DisplayName("회원가입 - 성공")
    void 회원가입_성공() {
        // given
        CustomerCreateRequestDto dto = new CustomerCreateRequestDto(
            "email", "password", "010-0000-1111", "이름"
        );

        given(customerRepository.existsByEmail(anyString()))
            .willReturn(false);

        given(customerRepository.save(any()))
            .willReturn(Customer.builder()
                .id(1L)
                .email(dto.getEmail())
                .password(dto.getPassword())
                .phoneNumber(dto.getPhoneNumber())
                .name(dto.getName())
                .role(Role.CUSTOMER)
                .build()
            );

        // when
        Customer customer = customerService.signUp(dto);

        // then
        assertThat(customer.getId()).isEqualTo(1L);
        assertThat(customer.getEmail()).isEqualTo(dto.getEmail());
        assertThat(customer.getName()).isEqualTo(dto.getName());
        assertThat(customer.getPassword()).isEqualTo(dto.getPassword());
        assertThat(customer.getPhoneNumber()).isEqualTo(dto.getPhoneNumber());
        assertThat(customer.getRole()).isEqualTo(Role.CUSTOMER);

    }

    // exception이 발생한다는 것 까지만 테스트 가능
    // 만약 추가적으로 에러 메시지를 까지 테스트 하고 싶으면 Exception 에 ErrorCode 를 넣거나
    // message를 관리해서 테스트 하기
    @Test
    @DisplayName("회원 가입 실패 - 중복 이메일 존재")
    void 회원가입_실패_중복_이메일() {

        CustomerCreateRequestDto dto = new CustomerCreateRequestDto(
            "email", "password", "010-0000-1111", "이름"
        );

        given(customerRepository.existsByEmail(anyString()))
            .willReturn(true);

        // when, then?
//        DuplicatedEmailException exception = assertThrows(DuplicatedEmailException.class,
//            () -> customerService.signUp(dto));

    }

}