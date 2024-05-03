package com.example.campingontop.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtils {
    public static String generateAccessToken(String email, String nickname, Long id, String key) {
        Claims claims = Jwts.claims();
        claims.put("email", email);
        claims.put("nickname", nickname);
        claims.put("id", id);

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date((System.currentTimeMillis() + 3600000) * 1000))
                .signWith(getSignKey(key), SignatureAlgorithm.HS256)
                .compact();

        return token;
    }

    public static Key getSignKey(String secretKey) {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public static Boolean validate(String token, String key) {
        Date expireTime = extractAllClaims(token, key).getExpiration();
        Boolean result = expireTime.before(new Date(System.currentTimeMillis()));

        return !result;
    }

    public static String getMemberEmail(String token, String key) {
        return extractAllClaims(token, key).get("email", String.class);

    }

    public static Long getMemberId(String token, String key) {
        return extractAllClaims(token, key).get("id", Long.class);
    }

    public static String getMemberNickname(String token, String key) {
        return extractAllClaims(token, key).get("nickname", String.class);
    }

    public static Claims extractAllClaims(String token, String key) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey(key))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
