package com.zerobase.reservation.controller;

import com.zerobase.reservation.domain.entity.Customer;
import com.zerobase.reservation.domain.entity.Owner;
import com.zerobase.reservation.dto.CustomerCreateRequestDto;
import com.zerobase.reservation.dto.OwnerCreateRequestDto;
import com.zerobase.reservation.service.CustomerService;
import com.zerobase.reservation.service.OwnerService;
import java.net.URI;
import java.net.URISyntaxException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 회원 가입 Controller
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/signup")
public class SignUpController {

    private final CustomerService customerService;
    private final OwnerService ownerService;

    /**
     * 고객 회원가입
     */
    @PostMapping("/customers")
    public ResponseEntity<String> customerSignUp(@RequestBody CustomerCreateRequestDto requestDto)
        throws URISyntaxException {

        Customer customer = customerService.signUp(requestDto);

        return ResponseEntity
            .created(createURI(customer.getId()))
            .body("{}");
    }

    /**
     * 점장 회원가입
     */
    @PostMapping("/owners")
    public ResponseEntity<String> ownerSignUp(@RequestBody OwnerCreateRequestDto requestDto)
        throws URISyntaxException {

        Owner owner = ownerService.signUp(requestDto);

        return ResponseEntity
            .created(createURI(owner.getId()))
            .body("{}");
    }

    // created 201 Location 생성
    private URI createURI(Long customerId) throws URISyntaxException {
        return new URI("/customers/" + customerId);
    }

}
