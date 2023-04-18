package com.zerobase.reservation.controller;

import com.zerobase.reservation.dto.RestaurantCreateRequestDto;
import com.zerobase.reservation.dto.RestaurantCreateResponseDto;
import com.zerobase.reservation.dto.RestaurantDetailDto;
import com.zerobase.reservation.dto.RestaurantDto;
import com.zerobase.reservation.service.RestaurantService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    // 매장 등록
    @PostMapping
    public RestaurantCreateResponseDto addRestaurant(RestaurantCreateRequestDto requestDto) {
        return restaurantService.addRestaurant(requestDto);
    }

    // 정렬 조건(order : name(가나다순), rating(별점))
    // 검색 조건(name : 매장이름)
    // 매장 조회
    @GetMapping
    public List<RestaurantDto> getRestaurants(
        @RequestParam(name = "order", required = false, defaultValue = "name") String orderCondition,
        @RequestParam(name = "name", required = false) String restaurantName
    ) {

        return restaurantService.getAllRestaurant(orderCondition, restaurantName);

    }

    // 매장 상세 조회
    @GetMapping("{restaurantId}")
    public RestaurantDetailDto getRestaurant(@PathVariable Long restaurantId) {

        return restaurantService.getRestaurantById(restaurantId);

    }


}
