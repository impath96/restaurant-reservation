package com.zerobase.reservation.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.zerobase.reservation.domain.entity.Restaurant;
import com.zerobase.reservation.domain.repository.RestaurantRepository;
import com.zerobase.reservation.dto.RestaurantCreateRequestDto;
import com.zerobase.reservation.dto.RestaurantCreateResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

    @InjectMocks
    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Test
    void addRestaurant() {

        String name = "restaurant01";
        String description = "일식 전문점입니다.";
        String phoneNumber = "010-1111-1111";
        String address = "서울시 강남구";

        Restaurant restaurant = Restaurant.builder()
            .id(1L)
            .name(name)
            .address(address)
            .phoneNumber(phoneNumber)
            .description(description)
            .build();

        given(restaurantRepository.save(any()))
            .willReturn(restaurant);

        RestaurantCreateResponseDto restaurantCreateResponseDto = restaurantService.addRestaurant(
            "email@naver.com",
            new RestaurantCreateRequestDto(name, phoneNumber, address, description)
        );

        assertThat(restaurantCreateResponseDto.getName()).isEqualTo(restaurant.getName());

    }
}