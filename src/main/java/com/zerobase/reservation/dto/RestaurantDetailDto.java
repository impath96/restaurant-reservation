package com.zerobase.reservation.dto;

import com.zerobase.reservation.domain.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantDetailDto {

    private Long restaurantId;

    private String name;

    private String address;

    private String description;

    public static RestaurantDetailDto fromEntity(Restaurant restaurant) {
        return RestaurantDetailDto.builder()
            .restaurantId(restaurant.getId())
            .name(restaurant.getName())
            .address(restaurant.getAddress())
            .description(restaurant.getDescription())
            .build();
    }


}
