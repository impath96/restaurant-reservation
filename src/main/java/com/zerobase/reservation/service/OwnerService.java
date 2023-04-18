package com.zerobase.reservation.service;

import com.zerobase.reservation.configuration.jwt.JwtTokenProvider;
import com.zerobase.reservation.domain.entity.Owner;
import com.zerobase.reservation.domain.repository.OwnerRepository;
import com.zerobase.reservation.dto.LogInForm;
import com.zerobase.reservation.dto.OwnerCreateRequestDto;
import com.zerobase.reservation.exception.DuplicatedEmailException;
import com.zerobase.reservation.exception.LogInFailException;
import com.zerobase.reservation.exception.OwnerAlreadyPartnerException;
import com.zerobase.reservation.exception.UserNotFoundException;
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
            throw new DuplicatedEmailException(email);
        }

        Owner owner = Owner.builder()
            .email(ownerCreateRequestDto.getEmail())
            .name(ownerCreateRequestDto.getName())
            .password(ownerCreateRequestDto.getPassword())
            .phoneNumber(ownerCreateRequestDto.getPhoneNumber())
            .role(Role.OWNER)
            .isPartner(false)
            .build();

        return ownerRepository.save(owner);
    }

    @Transactional
    public String logIn(LogInForm form) {

        Owner owner = ownerRepository.findByEmailAndPassword(
                form.getEmail(),
                form.getPassword())
            .orElseThrow(() -> new LogInFailException());

        return jwtTokenProvider.createToken(owner.getEmail(), owner.getId(),
            owner.getRole());

    }

    @Transactional
    public Owner registerPartner(String token) {

        // 먼저 해당 토큰 유효성 검사 - filter 또는 Interceptor 에서 구현!!
        String email = jwtTokenProvider.getEmail(token);

        Owner owner = ownerRepository.findByEmail(email)
            .orElseThrow(() -> new UserNotFoundException(email));

        // 이미 파트너 인 경우
        if (owner.isPartner()) {
            throw new OwnerAlreadyPartnerException();
        }
        owner.registerPartner();
        return ownerRepository.save(owner);
    }

    public boolean isExistsByEmail(String email) {
        return ownerRepository.existsByEmail(email);
    }
}