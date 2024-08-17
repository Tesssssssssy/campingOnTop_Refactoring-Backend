package com.example.campingontop.domain.mysql.houseImage.repository;

import com.example.campingontop.domain.mysql.houseImage.model.HouseImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseImageRepository extends JpaRepository<HouseImage, Long> {

}