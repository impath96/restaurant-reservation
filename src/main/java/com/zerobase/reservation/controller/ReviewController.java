package com.zerobase.reservation.controller;

import com.zerobase.reservation.configuration.jwt.JwtTokenProvider;
import com.zerobase.reservation.dto.ReviewCreateRequestDto;
import com.zerobase.reservation.dto.User;
import com.zerobase.reservation.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private static final String AUTH_TOKEN = "X-AUTH-TOKEN";

    private final JwtTokenProvider jwtTokenProvider;
    private final ReviewService reviewService;
    @PostMapping
    public ResponseEntity<String> write(
        @RequestHeader(name = AUTH_TOKEN) String token,
        @RequestBody ReviewCreateRequestDto requestDto
    ) {

        User user = jwtTokenProvider.getUser(token);

        reviewService.writeReview(user.getId(), requestDto);

        return ResponseEntity.ok("리뷰 작성 완료");

    }

}
