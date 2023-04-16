package com.zerobase.reservation.service;

import com.zerobase.reservation.domain.entity.Restaurant;
import com.zerobase.reservation.domain.repository.RestaurantRepository;
import com.zerobase.reservation.dto.RestaurantCreateRequestDto;
import com.zerobase.reservation.dto.RestaurantCreateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    // 매장 등록
    public RestaurantCreateResponseDto addRestaurant(RestaurantCreateRequestDto requestDto) {

        return RestaurantCreateResponseDto.fromEntity(
            restaurantRepository.save(
                Restaurant.builder()
                    .name(requestDto.getName())
                    .address(requestDto.getAddress())
                    .description(requestDto.getDescription())
                    .phoneNumber(requestDto.getPhoneNumber())
                    .build()
            )
        );
    }



}
