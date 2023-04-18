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
public class RestaurantDto {

    private Long restaurantId;
    private String name;
    private String description;

    public static RestaurantDto fromEntity(Restaurant restaurant) {
        return new RestaurantDto(restaurant.getId(), restaurant.getName(),
            restaurant.getDescription());
    }

}
