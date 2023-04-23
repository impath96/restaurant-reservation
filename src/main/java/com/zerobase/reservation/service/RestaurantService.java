package com.zerobase.reservation.service;

import com.zerobase.reservation.domain.entity.Owner;
import com.zerobase.reservation.domain.entity.Restaurant;
import com.zerobase.reservation.domain.repository.OwnerRepository;
import com.zerobase.reservation.domain.repository.RestaurantRepository;
import com.zerobase.reservation.dto.RestaurantCreateRequestDto;
import com.zerobase.reservation.dto.RestaurantCreateResponseDto;
import com.zerobase.reservation.dto.RestaurantDetailDto;
import com.zerobase.reservation.dto.RestaurantDto;
import com.zerobase.reservation.exception.CustomException;
import com.zerobase.reservation.exception.ErrorCode;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final OwnerRepository ownerRepository;

    // 매장 등록
    public RestaurantCreateResponseDto addRestaurant(
        String  ownerEmail,
        RestaurantCreateRequestDto requestDto) {

        Owner owner = findOwnerByEmail(ownerEmail);

        // 점장이 파트너 가입 되어 있는지 확인
        if (!owner.isPartner()) {
            throw new CustomException(ErrorCode.OWNER_NOT_PARTNER);
        }

        return RestaurantCreateResponseDto.fromEntity(
            restaurantRepository.save(
                Restaurant.builder()
                    .name(requestDto.getName())
                    .address(requestDto.getAddress())
                    .description(requestDto.getDescription())
                    .phoneNumber(requestDto.getPhoneNumber())
                    .owner(owner)
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
        Restaurant restaurant = findRestaurantById(restaurantId);

        return RestaurantDetailDto.fromEntity(restaurant);

    }

    private Owner findOwnerByEmail(String ownerEmail) {
        return ownerRepository.findByEmail(ownerEmail)
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    private Restaurant findRestaurantById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
            .orElseThrow(() -> new CustomException(ErrorCode.RESTAURANT_NOT_FOUND));
    }


}
