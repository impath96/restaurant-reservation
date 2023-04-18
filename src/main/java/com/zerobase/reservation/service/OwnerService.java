package com.zerobase.reservation.service;

import com.zerobase.reservation.configuration.jwt.JwtTokenProvider;
import com.zerobase.reservation.domain.entity.Owner;
import com.zerobase.reservation.domain.repository.OwnerRepository;
import com.zerobase.reservation.dto.LogInForm;
import com.zerobase.reservation.dto.OwnerCreateRequestDto;
import com.zerobase.reservation.exception.DuplicatedEmailException;
import com.zerobase.reservation.exception.LogInFailException;
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

        if (ownerRepository.existsByEmail(email)) {
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
}
