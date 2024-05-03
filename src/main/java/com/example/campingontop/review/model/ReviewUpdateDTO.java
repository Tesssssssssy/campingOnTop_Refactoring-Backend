package com.example.campingontop.review.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
@Builder
public class ReviewUpdateDTO {
    private String title;
    private String content;
    private int rating;
    private MultipartFile[] images;

}
