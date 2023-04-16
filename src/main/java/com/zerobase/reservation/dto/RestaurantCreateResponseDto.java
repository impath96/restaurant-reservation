package com.zerobase.reservation.dto;

import com.zerobase.reservation.domain.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RestaurantCreateResponseDto {

    private String name;
    private String description;

    public static RestaurantCreateResponseDto fromEntity(Restaurant restaurant) {
        return new RestaurantCreateResponseDto(restaurant.getName(), restaurant.getDescription());
    }
}
