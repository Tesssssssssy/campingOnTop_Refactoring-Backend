package com.example.campingontop.domain.mysql.house.repository;

import com.example.campingontop.domain.mysql.house.model.House;
import com.example.campingontop.domain.mysql.house.repository.queryDsl.HouseRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HouseRepository extends JpaRepository<House, Long>, HouseRepositoryCustom {
    public Optional<House> findByName(String name);
    Optional<House> findByUserId(Long userId);
}
