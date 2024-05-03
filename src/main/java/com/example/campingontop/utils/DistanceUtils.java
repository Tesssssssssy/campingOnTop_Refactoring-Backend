package com.example.campingontop.utils;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;

public class DistanceUtils {

    public static final double EARTH_RADIUS = 6371.0; // 지구 반지름 (단위: km)

    public static NumberTemplate<Double> calculateDistance(Double lat1, Double lon1, Double lat2, Double lon2) {
        NumberTemplate<Double> dLat = Expressions.numberTemplate(Double.class, "radians({0} - {1})", lat2, lat1);
        NumberTemplate<Double> dLon = Expressions.numberTemplate(Double.class, "radians({0} - {1})", lon2, lon1);

        NumberTemplate<Double> a = Expressions.numberTemplate(Double.class,
                "sin({0} / 2) * sin({0} / 2) + cos(radians({1})) * cos(radians({2})) * sin({1} / 2) * sin({1} / 2)",
                dLat, lat1, lat2);

        NumberTemplate<Double> c = Expressions.numberTemplate(Double.class,
                "2 * atan2(sqrt({0}), sqrt(1 - {0}))", a);

        return Expressions.numberTemplate(Double.class, "{0} * {1}", EARTH_RADIUS, c);
    }
}