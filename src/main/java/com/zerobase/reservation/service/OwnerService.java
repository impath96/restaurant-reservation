package com.zerobase.reservation.service;

import com.zerobase.reservation.configuration.jwt.JwtTokenProvider;
import com.zerobase.reservation.domain.entity.Owner;
import com.zerobase.reservation.domain.repository.OwnerRepository;
import com.zerobase.reservation.dto.LogInForm;
import com.zerobase.reservation.dto.OwnerCreateRequestDto;
import com.zerobase.reservation.exception.CustomException;
import com.zerobase.reservation.exception.ErrorCode;
import com.zerobase.reservation.type.Role;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;

    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public Owner signUp(OwnerCreateRequestDto ownerCreateRequestDto) {

        // 이메일 중복 체크
        String email = ownerCreateRequestDto.getEmail();

        if (isExistsByEmail(email)) {
            throw new CustomException(ErrorCode.USER_DUPLICATED_EMAIL);
        }

        return ownerRepository.save(
            Owner.builder()
                .email(email)
                .name(ownerCreateRequestDto.getName())
                .password(ownerCreateRequestDto.getPassword())
                .phoneNumber(ownerCreateRequestDto.getPhoneNumber())
                .role(Role.OWNER)
                .isPartner(false)
                .build()
        );
    }

    @Transactional
    public String logIn(LogInForm form) {

        Owner owner = ownerRepository.findByEmailAndPassword(
                form.getEmail(),
                form.getPassword())
            .orElseThrow(() -> new CustomException(ErrorCode.USER_LOGIN_FAIL));

        return jwtTokenProvider.createToken(owner.getEmail(), owner.getId(),
            owner.getRole());

    }

    @Transactional
    public Owner registerPartner(Long ownerId) {

        // 먼저 해당 토큰 유효성 검사 - filter 또는 Interceptor 에서 구현!!
        Owner owner = ownerRepository.findById(ownerId)
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 이미 파트너 인 경우
        if (owner.isPartner()) {
            throw new CustomException(ErrorCode.OWNER_ALREADY_PARTNER);
        }

        owner.registerPartner();

        return ownerRepository.save(owner);
    }

    private boolean isExistsByEmail(String email) {
        return ownerRepository.existsByEmail(email);
    }
}
