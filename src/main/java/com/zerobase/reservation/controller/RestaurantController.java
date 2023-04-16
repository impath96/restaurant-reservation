package com.zerobase.reservation.controller;

import com.zerobase.reservation.dto.RestaurantCreateRequestDto;
import com.zerobase.reservation.dto.RestaurantCreateResponseDto;
import com.zerobase.reservation.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public RestaurantCreateResponseDto addRestaurant(RestaurantCreateRequestDto requestDto) {
        return restaurantService.addRestaurant(requestDto);
    }


}
