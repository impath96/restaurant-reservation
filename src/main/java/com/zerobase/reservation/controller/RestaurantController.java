package com.zerobase.reservation.controller;

import com.zerobase.reservation.configuration.jwt.JwtTokenProvider;
import com.zerobase.reservation.dto.RestaurantCreateRequestDto;
import com.zerobase.reservation.dto.RestaurantCreateResponseDto;
import com.zerobase.reservation.dto.RestaurantDetailDto;
import com.zerobase.reservation.dto.RestaurantDto;
import com.zerobase.reservation.dto.User;
import com.zerobase.reservation.exception.CustomException;
import com.zerobase.reservation.exception.ErrorCode;
import com.zerobase.reservation.service.RestaurantService;
import com.zerobase.reservation.type.Role;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {

    private static final String AUTH_TOKEN = "X-AUTH-TOKEN";

    private final RestaurantService restaurantService;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 매장 등록(점장 용)
     */
    @PostMapping
    public RestaurantCreateResponseDto addRestaurant(
        @RequestHeader(name = AUTH_TOKEN) String token,
        @RequestBody RestaurantCreateRequestDto requestDto
    ) {

        User user = jwtTokenProvider.getUser(token);

        // 회원 권한 체크
        if (!isOwnerRole(user.getRole())) {
            throw new CustomException(ErrorCode.USER_ROLE_NOT_OWNER);
        }

        return restaurantService.addRestaurant(user.getId(), requestDto);
    }

    /**
     * 매장 조회
     * 1) 정렬 조건 : 가나다순, 별점순
     * 2) 검색 조건 : 매장 이름
     */
    @GetMapping
    public List<RestaurantDto> getRestaurants(
        @RequestParam(name = "order", required = false, defaultValue = "name") String orderCondition,
        @RequestParam(name = "name", required = false) String restaurantName
    ) {

        return restaurantService.getAllRestaurant(orderCondition, restaurantName);

    }

    /**
     * 매장 상세 조회
     */
    @GetMapping("{restaurantId}")
    public RestaurantDetailDto getRestaurant(@PathVariable Long restaurantId) {

        return restaurantService.getRestaurantById(restaurantId);

    }

    // 권한이 Owner 인지 체크
    private boolean isOwnerRole(Role role) {
        return role == Role.OWNER;
    }


}
