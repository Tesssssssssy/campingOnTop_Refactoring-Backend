package com.example.campingontop.enums;

public enum Gender {
    M, F;

    public static Gender fromValue(String value) {
        for (Gender gender : Gender.values()) {
            if (gender.name().equalsIgnoreCase(value)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("value에 해당하는 Gender Enum 존재 x: " + value);
    }
}
