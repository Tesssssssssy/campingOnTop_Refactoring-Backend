package com.example.campingontop.reviewImage.service;

import com.example.campingontop.aws.service.S3Service;
import com.example.campingontop.reviewImage.model.ReviewImage;
import com.example.campingontop.reviewImage.repository.ReviewImageRepository;
import com.example.campingontop.review.model.Review;
import com.example.campingontop.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ReviewImageService {
    private final S3Service s3Service;
    private final ReviewImageRepository reviewImageRepository;

    public void createImage(Long id, MultipartFile[] images) {
        for (MultipartFile image : images) {
            String savePath = ImageUtils.makeReviewImagePath(image.getOriginalFilename());  // 확인,
            savePath = s3Service.uploadFile(image, savePath);   // 확인

            ReviewImage img = ReviewImage.builder()
                    .filename(savePath)
                    .review(Review.builder().id(id).build())
                    .build();

            reviewImageRepository.save(img);
        }
    }
}
