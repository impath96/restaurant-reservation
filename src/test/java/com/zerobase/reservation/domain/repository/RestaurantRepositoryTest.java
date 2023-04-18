package com.zerobase.reservation.domain.repository;

import com.zerobase.reservation.domain.entity.Restaurant;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class RestaurantRepositoryTest {

    @Autowired
    RestaurantRepository restaurantRepository;

    @BeforeEach
    void init() {

        List<Restaurant> list = new ArrayList<>();

        list.add(Restaurant.builder().id(1L).name("가1").build());
        list.add(Restaurant.builder().id(2L).name("가2").build());
        list.add(Restaurant.builder().id(3L).name("가3").build());
        list.add(Restaurant.builder().id(4L).name("나1").build());
        list.add(Restaurant.builder().id(5L).name("다1").build());
        list.add(Restaurant.builder().id(6L).name("나2").build());

        restaurantRepository.saveAll(list);

    }
    @Test
    void 매장조회() {

        // given
        // when
        // then
        List<Restaurant> all = restaurantRepository.findAllByOrderByNameAsc();
        System.out.println("\n\n\n ============================================ ");
        all.stream()
            .forEach(System.out::println);
        System.out.println("\n\n\n ============================================ ");
    }

}