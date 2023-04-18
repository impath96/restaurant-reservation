package com.zerobase.reservation.domain.repository;

import com.zerobase.reservation.domain.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

    boolean existsByEmail(String email);
}
