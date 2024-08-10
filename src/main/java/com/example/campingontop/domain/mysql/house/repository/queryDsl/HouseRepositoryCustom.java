package com.example.campingontop.domain.mysql.house.repository.queryDsl;

import com.example.campingontop.domain.mysql.house.model.House;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface HouseRepositoryCustom {
    Page<House> findList(Pageable pageable);

    Optional<House> findActiveHouse(Long id);

    Optional<House> findActiveHouseForDelete(Long userId, Long id);

    Page<House> findByPriceDesc(Pageable pageable);

    Page<House> findByPriceAsc(Pageable pageable);
    Page<House> findByLikeCntDesc(Pageable pageable);

    Page<House> findByName(Pageable pageable, String name);


    List<House> getNearestHouseList(Double latitude, Double longitude);

    Page<House> findByAddress(Pageable pageable, String address);

    Page<House> findByReviewCntDesc(Pageable pageable);



}
