package com.zerobase.reservation.service;

import com.zerobase.reservation.domain.entity.Restaurant;
import com.zerobase.reservation.domain.repository.RestaurantRepository;
import com.zerobase.reservation.dto.RestaurantCreateRequestDto;
import com.zerobase.reservation.dto.RestaurantCreateResponseDto;
import com.zerobase.reservation.dto.RestaurantDetailDto;
import com.zerobase.reservation.dto.RestaurantDto;
import com.zerobase.reservation.exception.RestaurantNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
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


    public List<RestaurantDto> getAllRestaurant(String orderCondition,
        String restaurantName) {

        // 매장 이름이 null일 경우 그냥 정렬 기준으로만 출력
        if (restaurantName == null || restaurantName.equals("")) {

            switch (orderCondition) {
                case "name" :
                    return restaurantRepository.findAllByOrderByNameAsc()
                        .stream()
                        .map(RestaurantDto::fromEntity)
                        .collect(Collectors.toList());
                case "rating" :
                    return restaurantRepository.findAll()
                        .stream()
                        .map(RestaurantDto::fromEntity)
                        .collect(Collectors.toList());
            }
        }

        return restaurantRepository.findAllByNameContainingOrderByNameDesc(restaurantName)
            .stream()
            .map(RestaurantDto::fromEntity)
            .collect(Collectors.toList());
    }


    public RestaurantDetailDto getRestaurantById(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
            .orElseThrow(() -> new RestaurantNotFoundException());

        return RestaurantDetailDto.fromEntity(restaurant);

    }


}
