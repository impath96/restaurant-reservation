package com.zerobase.reservation.service;

import com.zerobase.reservation.domain.entity.Owner;
import com.zerobase.reservation.domain.entity.Restaurant;
import com.zerobase.reservation.domain.repository.OwnerRepository;
import com.zerobase.reservation.domain.repository.RestaurantRepository;
import com.zerobase.reservation.dto.RestaurantCreateRequestDto;
import com.zerobase.reservation.dto.RestaurantCreateResponseDto;
import com.zerobase.reservation.dto.RestaurantDetailDto;
import com.zerobase.reservation.dto.RestaurantDto;
import com.zerobase.reservation.exception.OwnerNotPartnerException;
import com.zerobase.reservation.exception.RestaurantNotFoundException;
import com.zerobase.reservation.exception.UserNotFoundException;
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

        Owner owner = ownerRepository.findByEmail(ownerEmail)
            .orElseThrow(() -> new UserNotFoundException(ownerEmail));

        // 점장이 파트너 가입 되어 있는지 확인
        if (!owner.isPartner()) {
            throw new OwnerNotPartnerException();
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
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
            .orElseThrow(() -> new RestaurantNotFoundException());

        return RestaurantDetailDto.fromEntity(restaurant);

    }


}
