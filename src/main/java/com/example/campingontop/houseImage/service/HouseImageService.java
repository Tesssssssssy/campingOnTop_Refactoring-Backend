package com.example.campingontop.houseImage.service;

import com.example.campingontop.aws.service.S3Service;
import com.example.campingontop.house.model.House;
import com.example.campingontop.houseImage.model.HouseImage;
import com.example.campingontop.houseImage.repository.HouseImageRepository;
import com.example.campingontop.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class HouseImageService {
    private final S3Service s3Service;
    private final HouseImageRepository houseImageRepository;

    public void createHouseImage(Long id, MultipartFile[] images) {
        for (MultipartFile image : images) {
            String savePath = ImageUtils.makeHouseImagePath(image.getOriginalFilename());
            savePath = s3Service.uploadFile(image, savePath);

            HouseImage houseImage = HouseImage.builder()
                    .filename(savePath)
                    .house(House.builder().id(id).build())
                    .build();

            houseImageRepository.save(houseImage);
        }
    }
}
