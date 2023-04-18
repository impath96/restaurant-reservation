package com.zerobase.reservation.domain.repository;

import com.zerobase.reservation.domain.entity.Restaurant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findAllByOrderByNameAsc();
    List<Restaurant> findAllByNameContainingOrderByNameDesc(String name);
}
