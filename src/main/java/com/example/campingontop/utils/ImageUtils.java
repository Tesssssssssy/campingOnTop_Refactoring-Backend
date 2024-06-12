package com.example.campingontop.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class ImageUtils {
    public static String makeHouseImagePath(String originalName) {
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String folderPath = str;
        String type = "house";
        String uuid = UUID.randomUUID().toString();

        return type + "/" + folderPath + "/" + uuid + "_" + originalName;
    }

    public static String makeReviewImagePath(String originalName) {
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String folderPath = str;
        String type = "review";
        String uuid = UUID.randomUUID().toString();

        return type + "/" + folderPath + "/" + uuid + "_" + originalName;
    }
}
